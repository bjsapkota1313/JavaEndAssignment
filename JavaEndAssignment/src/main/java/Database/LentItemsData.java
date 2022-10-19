package Database;

import Model.LentItem;
import java.util.ArrayList;
import java.util.List;

public class LentItemsData {
    private final List<LentItem> lentItems;

    public LentItemsData() {

        lentItems= new ArrayList<LentItem>();
    }
    public void addLentItem(LentItem lentItem) {

        lentItems.add(lentItem);
    }
    public void removeLentItem(LentItem lentItem){
        lentItems.remove(lentItem);
    }
    public List<LentItem> getLentItems() {

        return lentItems;
    }
    public LentItem getLentItemWithLentItems(int libraryItemCode){
        LentItem returningLentItem=null;
        for (LentItem lentItem:lentItems
             ) {
            // looking in lending Item if the library Item is there or not
                if(lentItem.getItem().getItemCode() == libraryItemCode){
                    returningLentItem=lentItem;
                }
        }
        return returningLentItem;
    }
}
