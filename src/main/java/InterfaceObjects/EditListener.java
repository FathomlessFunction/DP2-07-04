package InterfaceObjects;

import DataObjects.Sale;

/**
 * Making a whole interface for this is a little overkill, but it saves lines of code in the ReturnHotbar class
 */
public interface EditListener {
    void editClicked(Object [] sale);
}
