package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResultEndPointByStationNumberDTO {
	
	private List<PersonDTO> listPersonDTO;
	
	private Integer adultsCounter ;
	
	private Integer childsCounter;

}
