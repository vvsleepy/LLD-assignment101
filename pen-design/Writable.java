// Default package used so VSCode Run button works

// This interface is the common contract for anything that can write (Pen, Pencil, etc.)
// A pencil in the future just needs to implement these 3 basic methods
public interface Writable {
    void start();
    void write();
    void close();
}
