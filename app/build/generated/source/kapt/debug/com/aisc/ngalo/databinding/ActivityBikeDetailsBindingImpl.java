package com.aisc.ngalo.databinding;
import com.aisc.ngalo.R;
import com.aisc.ngalo.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityBikeDetailsBindingImpl extends ActivityBikeDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar3, 1);
        sViewsWithIds.put(R.id.backarrow, 2);
        sViewsWithIds.put(R.id.details, 3);
        sViewsWithIds.put(R.id.cart, 4);
        sViewsWithIds.put(R.id.cartValue, 5);
        sViewsWithIds.put(R.id.bikeImage, 6);
        sViewsWithIds.put(R.id.textViewsContainer, 7);
        sViewsWithIds.put(R.id.textBikeName, 8);
        sViewsWithIds.put(R.id.textViewPrice, 9);
        sViewsWithIds.put(R.id.detailsDesc, 10);
        sViewsWithIds.put(R.id.textViewDesc, 11);
        sViewsWithIds.put(R.id.checkoutBtn, 12);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityBikeDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private ActivityBikeDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.Button) bindings[12]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[9]
            , (android.widget.LinearLayout) bindings[7]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}