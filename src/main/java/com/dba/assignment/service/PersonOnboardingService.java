package com.dba.assignment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dba.assignment.dto.PersonDto;
import com.dba.assignment.dto.SkillsDto;
import com.dba.assignment.entity.Dept;
import com.dba.assignment.entity.EmpSkill;
import com.dba.assignment.entity.EmpSkillPK;
import com.dba.assignment.entity.Employee;
import com.dba.assignment.entity.Project;
import com.dba.assignment.entity.Role;
import com.dba.assignment.entity.ServiceLog;
import com.dba.assignment.entity.ServiceLogContain;
import com.dba.assignment.entity.ServiceLogContainPK;
import com.dba.assignment.entity.Skill;
import com.dba.assignment.entity.UserCredentials;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.DeptRepository;
import com.dba.assignment.repository.EmpSkillRepository;
import com.dba.assignment.repository.EmployeeRepository;
import com.dba.assignment.repository.ProjectRepository;
import com.dba.assignment.repository.RoleRepository;
import com.dba.assignment.repository.ServiceLogContainRepository;
import com.dba.assignment.repository.SkillRepository;
import com.dba.assignment.repository.UserCredentialsRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonOnboardingService {
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private DeptRepository deptRepo;
	
	
	@Autowired
	private EmpSkillRepository empSkillRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ServiceLogContainRepository serviceLogRepo;
	
	@Autowired
	private RoleRepository rolesRepo;
	
	@Autowired
	private SkillRepository skillRepo;
	
	@Autowired
	private UserCredentialsRepository credentialRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional	
	public Employee onboardPerson(PersonDto person) {
		ModelMapper modelMapper = new ModelMapper();
		
		PropertyMap<PersonDto, Employee> skipModifiedFieldsMap = new PropertyMap<>() {
		      protected void configure() {
		         skip().setId(null);
		         
		     }
		   };
		   
		modelMapper.addMappings(skipModifiedFieldsMap);
		
		Employee e = modelMapper.map(person, Employee.class);
		Dept dept = deptRepo.findById(person.getDeptid()).orElseThrow(
				() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept does not exist.")));

		Set<Role> empRoles = null;
		Supplier<BadRequestException> roleExceptionSup = ()-> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "One of the Role does not exist."));
		
		if(person.getRoleIds()!= null ) {
			empRoles = person.getRoleIds().stream().map(rid->rolesRepo.findById(rid).orElseThrow(roleExceptionSup)).collect(Collectors.toSet());
		
		} else {
			empRoles = Stream.of(rolesRepo.findById(Constant.DEFAULT_USER_ROLE).orElseThrow(roleExceptionSup)).collect(Collectors.toSet());
		}
		
		
		String currency = dept.getLocations().stream().map(lo -> lo.getCountry()).map(c -> c.getCurrency()).findFirst()
				.orElseThrow(() -> new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept does not exist.")));
		e.setCurrency(currency);
		e.setSalary(person.getSalary());
		if (person.getProjectId() != null) {
			Project project = projectRepo.findById(person.getProjectId()).orElseThrow(() -> new BadRequestException(
					new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Project does not exist.")));
			if(project.getDept().getId()!= dept.getId()) {
				throw new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "The Project does not exist for this dept."));
			}
			
			e.setProjects(Stream.of(project).collect(Collectors.toSet()));
		}		
		
		Optional.ofNullable(person.getSupervisorId()).ifPresent(supervisor->{
			Employee supervisorEmp = employeeRepo.findById(supervisor).orElseThrow(() -> new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Supervisor does not exist.")));
			e.setSupervisor(supervisorEmp);
		});
		
		e.setDepartment(dept);
		e.setRoles(empRoles);
		ServiceLog serviceInfo = new ServiceLog();
		e.setServiceInfo(serviceInfo);
				
		
		Employee empSaved = employeeRepo.save(e);
		
		UserCredentials credential = new UserCredentials();
		credential.setUserName(person.getUserName());
		credential.setPassword(passwordEncoder.encode(person.getPassword()));
		credential.setCreateOrUpdatedAt(new Date());
		credential.setEmailId(person.getEmailId());
		
		credential.setEmp(empSaved);
		
		credentialRepo.save(credential);
		
		return empSaved;
	}
	
	public void updateSkill() {
		
	}
	
	
	public List<Employee> getEmployeeListForDept(final Integer deptId) {
		return employeeRepo.findByDept(deptId);
	}
	
	public List<Employee> getSupervisorForDept(final Integer deptId) {
		return employeeRepo.findSupervisorsByDept(deptId);
	}
		
	public Employee editPerson(long personId, PersonDto person) {
		Employee e = employeeRepo.findById(personId).orElseThrow(
				() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee does not exist.")));
		
		Dept dept = deptRepo.findById(person.getDeptid()).orElseThrow(
				() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept does not exist.")));

		String currency = dept.getLocations().stream().map(lo -> lo.getCountry()).map(c -> c.getCurrency()).findFirst()
				.orElseThrow(() -> new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept does not exist.")));
		e.setCurrency(currency);
		
		Optional.ofNullable(person).map(p-> { 
			e.setName(p.getName());
//		    e.setCurrency(p.getCurrency());
		    e.setDateOfBirth(p.getDateOfBirth());
		    e.setDateOfJoining(p.getDateOfJoining());
		    e.setTitle(p.getTitle());
		    
		    return e;  
		});
		
		return employeeRepo.save(e);     	
		
	}
	
	public void editSkills(long personId, SkillsDto skillsDto) {
		Employee e = employeeRepo.findById(personId).orElse(null);
		
//		Map<Integer, EmpSkill> empSkillMap = empSkillRepo.findByEmpId(personId).stream().collect(Collectors.toMap(EmpSkill::EmpSkillPK::getSkillId, Function.identity()));
		Map<Integer, EmpSkill> empSkillMap = empSkillRepo.findByEmpId(personId).stream().collect(Collectors.toMap(p->p.getId().getSkillId(), p->p));

		
		skillsDto.getEmpSkills().forEach(dto->{
			EmpSkill eSkill = empSkillMap.get(dto.getSkillId());
			
			if(eSkill == null) {
				EmpSkill newESkill = new EmpSkill();
				EmpSkillPK empSkillPK = new EmpSkillPK();
				empSkillPK.setEmpId(personId);
				empSkillPK.setSkillId(dto.getSkillId());
				newESkill.setId(empSkillPK);
				empSkillMap.put(dto.getSkillId(), newESkill);
				
			} else {
				eSkill.setProficiency(dto.getProficiency());
			}
			
		});		
	
		empSkillRepo.saveAll(empSkillMap.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList()));
		
	}
	
	public String allocateProject(long personId, long projectId, Date projectJoinDate) {
		
		Project p = projectRepo.findById(projectId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Project does not exist.")));
		
		return employeeRepo.findById(personId).map(emp-> { 
			
			if(emp.getDepartment().getId()!=p.getDept().getId()) {
				throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), String.format("Project %s does not exist for department %s.", p.getName(), emp.getDepartment().getName())));
			}
			
			emp.getProjects().add(p);
						
			ServiceLogContain empLog = new ServiceLogContain();
			ServiceLogContainPK pk = new ServiceLogContainPK();
			pk.setProjectId(p.getId());
			pk.setServiceLogId(emp.getServiceInfo().getId());
			empLog.setId(pk);
			
			empLog.setServiceLog(emp.getServiceInfo());
			empLog.setProject(p);
			empLog.setProjectJoinDate(Optional.ofNullable(projectJoinDate).orElse(new Date()));
			emp.getServiceInfo().getServiceLogProjects().add(empLog);
			employeeRepo.save(emp);
			return String.format("Project %s is allocated to %s", p.getName(), emp.getName());
		    
		}).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee does not exist.")));
	}
	
	
	@Transactional
	public String deallocateProject(long personId, long projectId, String comments, String grade) {
				
		Project p = projectRepo.findById(projectId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Project does not exist.")));
		
		Employee emp = employeeRepo.findById(personId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee does not exist.")));
		
		
		Set<Project> projects = emp.getProjects();
		boolean flag = projects.stream().anyMatch(prj->prj.getId()==projectId);
		
		if(!flag) {
			throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), String.format("Employee is not working on the project %s.", p.getName())));
		}
				
		projects.removeIf(prj->prj.getId()==projectId);			
		
		//emp.setProjects(projects);
		
//		Set<ServiceLogContain> projectLogSet = emp.getServiceInfo().getServiceLogProjects();
		
		ServiceLogContainPK pk = new ServiceLogContainPK();
		pk.setProjectId(p.getId());
		pk.setServiceLogId(emp.getServiceInfo().getId());
		ServiceLogContain newProjLog = new ServiceLogContain();
		newProjLog.setComment(comments);
		newProjLog.setProjectExitDate(new Date());
		newProjLog.setGrade(grade);
		newProjLog.setId(pk);
		newProjLog.setProject(p);
		newProjLog.setServiceLog(emp.getServiceInfo());
		newProjLog.setProjectExitDate(new Date());
//		projectLogSet.add(newProjLog);
		serviceLogRepo.save(newProjLog);
		employeeRepo.save(emp);
		
//		projectLogSet.forEach(projLog->{
//			if(projLog.getId().equals(pk)) {
//				projLog.setComment(comments);
//		        projLog.setProjectExitDate(new Date());
//		        projLog.setGrade(grade);
//		        emp.getServiceInfo().getServiceLogProjects().add(projLog);
//		        employeeRepo.save(emp);
//			}
//		});
		
		return String.format("%s is removed from the project %s", emp.getName(), p.getName());
		
	}
	
	public void changeDept(final Long personId, final Integer deptId, Double salaryAmt) {
		
		Dept changedDept = deptRepo.findById(deptId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept does not exist.")));
		Employee emp = employeeRepo.findById(personId)
				                         .orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee does not exist.")));
		
		String currentDept = emp.getDepartment().getName();
		Optional.ofNullable(emp.getProjects()).filter(pset->!pset.isEmpty()).map(projects->projects.stream().map(proj->proj.getName()).collect(Collectors.toList())).ifPresent(projectNames->{
			throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), String.format("Employee still works on the projects %s of department %s ", projectNames, currentDept)));
			
		});
		
		emp.setDepartment(changedDept);
		emp.setCurrency(changedDept.getLocations().stream().map(loc->loc.getCountry()).map(c->c.getCurrency()).findAny().get());
		emp.setSupervisor(null);
		Optional.ofNullable(salaryAmt).ifPresent(amt->emp.setSalary(amt));
		employeeRepo.save(emp);
		
	}
	
	public void addRemoveRole(Long empId, Integer roleId, boolean isRemove) {
		Employee emp = employeeRepo.findById(empId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee does not exist.")));
		
		boolean roleExist = emp.getRoles().stream().anyMatch(r1->r1.getId()==roleId);
		
		if(isRemove) {			
			if(!roleExist) {
				throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Role is not allocated to employee."));
			}
			emp.getRoles().removeIf(r->r.getId()==roleId);
		} else {
			if(roleExist) {
				throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Role is already allocated to employee."));
			}
			Role nextRole = rolesRepo.findById(roleId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Role does not exist.")));
			emp.getRoles().add(nextRole);
		}
		
		employeeRepo.save(emp);		
	}
	
	public void addRemoveSkill(Long empId, Integer skillId, boolean isRemove) {
		Employee emp = employeeRepo.findById(empId).orElseThrow(() -> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee does not exist.")));
		
		Skill skill = skillRepo.findById(skillId).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Skill doesn't exist in the system")));
		
	
		boolean empSkillExist = emp.getSkillMatrix().stream().anyMatch(s1->s1.getId().getSkillId()==skillId && s1.getId().getEmpId()==empId);
		Set<EmpSkill> skillmatrix = emp.getSkillMatrix();
		if(isRemove) {
			if(!empSkillExist) {
				throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee doesn;t possess the skill. Hence it cannot be removed."));
			}
//			skillmatrix.removeIf(s1->s1.getId().getSkillId()==skillId && s1.getId().getEmpId()==empId);
			empSkillRepo.deleteById(new EmpSkillPK(empId, skillId));
		} else {
			if(empSkillExist) {
				throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee already have the skill. Hence it cannot be added."));
			}
			
			EmpSkill eSkill = new EmpSkill();
			EmpSkillPK pk= new EmpSkillPK();
			pk.setEmpId(empId);
			pk.setSkillId(skillId);
			eSkill.setId(pk);
			eSkill.setEmp(emp);
			eSkill.setSkill(skill);
			eSkill.setProficiency(0);
			//skillmatrix.add(eSkill);
			empSkillRepo.save(eSkill);
		}
		
		//employeeRepo.save(emp);
		
		
	}
	
	public void updateProficiency(Long empId, Integer skillId, Integer proficiency) {

		
		EmpSkillPK pk= new EmpSkillPK(empId, skillId);
		EmpSkill eSkill = empSkillRepo.findById(pk).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee doesn't possess Skill")));

		eSkill.setProficiency(proficiency);
		empSkillRepo.save(eSkill);
		
	}
 	
	
	public Employee getEmployee(long id) {
		return employeeRepo.findById(id).orElse(null);
	}
	
	public List<Employee> getAllEmployee(int pageNo, int pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Employee> pagedResult = employeeRepo.findAll(paging);
		
		List<Employee> empList = new ArrayList<>(pagedResult.getNumberOfElements());
		if(pagedResult.hasContent()) {
            empList.addAll(pagedResult.getContent());
        } 
		return empList;
	}
}
