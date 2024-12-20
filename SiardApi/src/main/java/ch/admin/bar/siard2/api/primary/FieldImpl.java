package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Field;
import ch.admin.bar.siard2.api.MetaField;
import ch.admin.bar.siard2.api.MetaValue;
import ch.admin.bar.siard2.api.Value;
import ch.admin.bar.siard2.api.meta.MetaFieldImpl;

import java.io.IOException;

import org.w3c.dom.Element;


public class FieldImpl extends ValueImpl implements Field {
    private Cell _cellAncestor = null;

    public Cell getAncestorCell() {
        return this._cellAncestor;
    }

    private Value _valueParent = null;

    public Value getParent() {
        return this._valueParent;
    }

    private MetaField _mf = null;

    public MetaField getMetaField() {
        return this._mf;
    }


    private FieldImpl(int iIndex, Value valueParent, Cell cellAncestor, MetaField mf, Element elField) throws IOException {
        this._mf = mf;
        this._valueParent = valueParent;
        this._cellAncestor = cellAncestor;
        RecordImpl ri = (RecordImpl) cellAncestor.getParentRecord();
        initialize(ri.getRecord(), ri.getTemporaryLobFolder(), iIndex, elField, mf);
    }


    public static Field newInstance(int iIndex, Value valueParent, Cell cellAncestor, MetaField mf, Element elField) throws IOException {
        return new FieldImpl(iIndex, valueParent, cellAncestor, mf, elField);
    }


    protected Field createField(int iField, MetaField mf, Element el) throws IOException {
        if (el != null) {

            int iCardinality = mf.getCardinality();
            if (iCardinality > 0)
                extendArray(iField, iCardinality);
        }
        return newInstance(iField, this, getAncestorCell(), mf, el);
    }


    protected String getInternalLobFolder() throws IOException {
        return ((MetaFieldImpl) getMetaField()).getFolder();
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\primary\FieldImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */