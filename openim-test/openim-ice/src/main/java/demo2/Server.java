package demo2;

import Filesystem2.File;
import Filesystem2.GenericError;

/**
 * Created by shihuacai on 2015/10/25.
 */
public class Server extends Ice.Application {
    public int run(String[] args) {
        // Create an object adapter (stored in the _adapter
        // static members)
        //
        Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints(
                "SimpleFilesystem", "default -p 8008");
        DirectoryI._adapter = adapter;
        FileI._adapter = adapter;
        // Create the root directory (with name "/" and no parent)
        //
        DirectoryI root = new DirectoryI("C:\\ICEFileStystem", null);
        // Create a file "README" in the root directory
        //
        File file = new FileI("README", root);
        String[] text;
        text = new String[] {
                "This file system contains a collection of poetry."
        };
        try {
            file.write(text, null);
        } catch (GenericError e) {
            System.err.println(e.reason);
        }
// Create a directory "Coleridge" in the root directory//
        DirectoryI coleridge = new DirectoryI("Coleridge", root);
// Create a file "Kubla_Khan" in the Coleridge directory
//
        file = new FileI("Kubla_Khan", coleridge);
        text = new String[]{ "In Xanadu did Kubla Khan",
                "A stately pleasure-dome decree:",
                "Where Alph, the sacred river, ran",
                "Through caverns measureless to man",
                "Down to a sunless sea." };
        try {
            file.write(text, null);
        } catch (GenericError e) {
            System.err.println(e.reason);
        }
// All objects are created, allow client requests now
//
        adapter.activate();
// Wait until we are done
//
        communicator().waitForShutdown();
        return 0;
    }
    public static void main(String[] args) {
        Server app = new Server();
        System.exit(app.main("Server", args));
    }
}
