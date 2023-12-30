import { AdministrativeUnit } from "./AdministrativeUnit.model";

export class Person {
    shortName?: string = null;
    birthDate?: Date = null;
    birthPlace?: string = null;
    gender?: string = null;
    startDate?: Date = null;
    endDate?: Date = null;
    phoneNumber?: string = null;
    idNumber?: string = null;
    idCitizen?: string = null;
    idNumberIssueBy?: string = null;
    idNumberIssueDate?: Date = null;
    email?: string = null;
    // protected com.globits.core.dto.CountryDto nationality;
    nativeVillage?: AdministrativeUnit = null;
    // protected com.globits.core.dto.EthnicsDto ethnics;
    // protected com.globits.core.dto.ReligionDto religion;
    photo?: any[] = null;
    photoCropped?: boolean = null;
    imagePath?: string = null;
    addresses?: any[] = [];
    userId?: number = null;
    communistYouthUnionJoinDate?: Date = null;
    communistPartyJoinDate?: Date = null;
    carrer?: string = null;
    maritalStatus?: number = null;
}