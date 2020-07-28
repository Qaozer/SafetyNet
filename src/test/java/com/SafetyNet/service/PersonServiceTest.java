package com.SafetyNet.service;

import com.SafetyNet.dao.PersonDao;
import com.SafetyNet.model.MedicalRecord;
import com.SafetyNet.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonDao personDao;

    private List<Person> buildTwoIdenticalPersonList(){
        List<Person> toReturn = new ArrayList<>();
        List<Integer> stationList = new ArrayList<>();
        stationList.add(1);
        MedicalRecord MedA = new MedicalRecord("Bob","Moran","01/01/01", new ArrayList<>(), new ArrayList<>());
        Person A = new Person("Bob","Moran","1 rue de l'Aventurier","Piras",12345,"000-000-000","Bob@Moran.fr",MedA, stationList,119);
        toReturn.add(A);
        toReturn.add(A);
        return toReturn;
    }

    @Test
    public void getPersonsListFilteredByFirstAndLastNameShouldReturnListOfPersonsWhenMultiplePeopleHaveSameName(){
        when(personDao.getPersonsList()).thenReturn(buildTwoIdenticalPersonList());
        List<Person> test = personService.getPersonsListFilteredByFirstAndLastName("Bob","Moran");
        assertThat(test.size()).isEqualTo(2);
    }
}
