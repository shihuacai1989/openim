package demo2;

import Filesystem2.NodePrx;
import Filesystem2.NodePrxHelper;
import Filesystem2._DirectoryDisp;
import Ice.Current;

import java.util.UUID;

/**
 * Created by shihuacai on 2015/10/25.
 */
public class DirectoryI extends _DirectoryDisp {
    public static Ice.ObjectAdapter _adapter;
    private String _name;
    private DirectoryI _parent;
    private java.util.ArrayList _contents = new java.util.ArrayList();

    // Constructor and operations here...
    public DirectoryI(String name, DirectoryI parent) {
        _name = name;
        _parent = parent;
        // Create an identity. The parent has the
        // fixed identity "RootDir"
        //
        String uuid = UUID.randomUUID().toString();
        Ice.Identity myID = Ice.Util.stringToIdentity(_parent != null ? uuid : "RootDir");
        // Add the identity to the object adapter
        //
        _adapter.add(this, myID);
        // Create a proxy for the new node and add it as a
        // child to the parent
        //
        NodePrx thisNode
                = NodePrxHelper.uncheckedCast(_adapter.createProxy(myID));
        if (_parent != null)
            _parent.addChild(thisNode);
    }

    void addChild(NodePrx child) {
        _contents.add(child);
    }

    @Override
    public NodePrx[] list(Current __current) {
        NodePrx[] result = new NodePrx[_contents.size()];
        _contents.toArray(result);
        return result;
    }

    @Override
    public String name(Current __current) {
        return _name;
    }
}
