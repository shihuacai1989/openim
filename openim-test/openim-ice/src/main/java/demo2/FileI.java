package demo2;

import Filesystem2.GenericError;
import Filesystem2.NodePrx;
import Filesystem2.NodePrxHelper;
import Filesystem2._FileDisp;
import Ice.Current;

import java.util.UUID;

/**
 * Created by shihuacai on 2015/10/25.
 */
public class FileI extends _FileDisp {
    // Constructor and operations here...
    public static Ice.ObjectAdapter _adapter;
    private String _name;
    private DirectoryI _parent;
    private String[] _lines;

    public
    FileI(String name, DirectoryI parent)
    {
        _name = name;
        _parent = parent;
        assert(_parent != null);
        // Create an identity
        //
        //String uuid = Ice.Util.generateUUID();
        String uuid = UUID.randomUUID().toString();
        Ice.Identity myID = Ice.Util.stringToIdentity(uuid);
        // Add the identity to the object adapter
        //
        _adapter.add(this, myID);
        // Create a proxy for the new node and
        // add it as a child to the parent
        //
        NodePrx thisNode = NodePrxHelper.uncheckedCast(_adapter.createProxy(myID));
        _parent.addChild(thisNode);
    }

    @Override
    public String[] read(Current __current) {
        return _lines;
    }

    @Override
    public void write(String[] text, Current __current) throws GenericError {
        _lines = text;
    }

    @Override
    public String name(Current __current) {
        return _name;
    }
}
