package pl.piomin.services.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.execution.DefaultToolCallResultConverter;
import org.springframework.stereotype.Service;
import pl.piomin.services.model.Person;
import pl.piomin.services.repository.PersonRepository;

import java.util.List;

@Service
public class PersonTools {

    private final PersonRepository personRepository;

    public PersonTools(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Tool(description = "通过人员 ID 查找人")
    public Person getPersonById(@ToolParam(description = "人员 ID") Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Tool(description = "按国籍查找所有人")
    public List<Person> getPersonsByNationality(@ToolParam(description = "国籍") String nationality) {
        return personRepository.findByNationality(nationality);
    }

}
