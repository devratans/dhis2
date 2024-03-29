package org.hisp.dhis.coldchain.inventory;

import java.util.Collection;

import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.period.Period;

public interface EquipmentDataValueStore
{
    String ID = EquipmentDataValueStore.class.getName();
    
    // -------------------------------------------------------------------------
    // EquipmentDataValue
    // -------------------------------------------------------------------------

    void addEquipmentDataValue( EquipmentDataValue equipmentDataValue );
    
    void updateEquipmentDataValue( EquipmentDataValue equipmentDataValue );
    
    void deleteEquipmentDataValue( EquipmentDataValue equipmentDataValue );
    
    Collection<EquipmentDataValue> getEquipmentDataValues( EquipmentInstance equipmentInstance, Period period, Collection<DataElement> dataElements );
    
    EquipmentDataValue getEquipmentDataValue( EquipmentInstance equipmentInstance, Period period, DataElement dataElement );
}
