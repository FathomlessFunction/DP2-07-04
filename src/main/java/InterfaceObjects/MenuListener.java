package InterfaceObjects;

import DataObjects.Product;

/**
 * This takes an enum from the class that's using the listener make a selection
 */
public interface MenuListener {
    void menuSelection(Enum selection);
}
