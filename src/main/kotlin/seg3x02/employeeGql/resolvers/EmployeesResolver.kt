package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import java.util.UUID


@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {
    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @MutationMapping
    fun addEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput): Employee {
        if(input.name != null && input.dateOfBirth != null && input.city != null && input.salary != null) {
            val employee = Employee(
                name = input.name,
                dateOfBirth = input.dateOfBirth,
                city = input.city,
                salary = input.salary,
                gender = input.gender,
                email = input.email)
            employee.id = UUID.randomUUID().toString()
            employeesRepository.save(employee)
            return employee
        }
        else{
            throw Exception("Invalid input")
        }
    }

    //TODO: Figure out why this function is not working, what exactly is requested here
    //TODO: we might also need a getFunction to get all employees
    @MutationMapping
    fun newEmployee(createEmployeeInput: CreateEmployeeInput): Employee {
        if(createEmployeeInput.name != null && createEmployeeInput.dateOfBirth != null && createEmployeeInput.city != null && createEmployeeInput.salary != null) {
            val employee = Employee(
                // Map the fields from `createEmployeeInput` to `Employee`
                name = createEmployeeInput.name,
                dateOfBirth = createEmployeeInput.dateOfBirth,
                city = createEmployeeInput.city,
                salary = createEmployeeInput.salary,
                gender = createEmployeeInput.gender,
                email = createEmployeeInput.email
            )
        return employee
        }
        else{
            throw Exception("Invalid input")
        }
    }
}
