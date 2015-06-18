package pl.edu.agh.soa;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.AuthMethod;
import org.jboss.ws.api.annotation.TransportGuarantee;
import org.jboss.ws.api.annotation.WebContext;

import pl.edu.agh.soa.model.Student;



@Stateless
@WebService(
		name = "StudentPortType",
		portName= "StudentPort",
		targetNamespace ="http://soa.kis.agh.edu.pl/lab1")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
@SecurityDomain("soapLabWsSD")
@WebContext(transportGuarantee = TransportGuarantee.NONE, secureWSDLAccess = false, authMethod = AuthMethod.BASIC)
@RolesAllowed("AdminRole")
public class StudentWebservice {
	private List<Student> studentList;
	
	public StudentWebservice(){
		List<String> subjects = new ArrayList<String>();
		subjects.add("math");
		subjects.add("physic");
		
		studentList = new ArrayList<Student>();
		studentList.add(new Student("Aga", "Szczurek", "1", subjects));
		studentList.add(new Student("Piotr", "Konsek", "2", subjects));
		
	}
	
	@WebMethod
	public Student getStudent(@WebParam(name="pesel")String pesel){
		for(Student student : studentList)
			if(pesel.equals(student.getPesel()))
				return student;
		return null;
	}
	
	@WebMethod
	public List<Student> getStudentList(){
		return studentList;
	}
}
