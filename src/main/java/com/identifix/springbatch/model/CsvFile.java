package com.identifix.springbatch.model;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class CsvFile {

	@Id
	String connectorPartNumber;
	String cavityCount,nonUsedTwo,normalizedPartNumber,viewFile,viewExt,gender,nonUsedSeven,description,repairKitPartNumber,nonUsedTen,nonUsedEleven,isoFile,wireFile;

}
