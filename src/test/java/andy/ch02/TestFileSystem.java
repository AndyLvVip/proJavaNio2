package andy.ch02;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

/**
 * @Author: andy.lv
 * @Date: created on 2018/1/22
 * @Description:
 */
public class TestFileSystem {

    @Test
    public void supportedFileAttributeViews() {
        FileSystem fs = FileSystems.getDefault();
        Set<String> views = fs.supportedFileAttributeViews();
        views.forEach(System.out::println);
    }

    @Test
    public void fileStores() {
        FileSystems.getDefault().getFileStores()
                .forEach(fs -> {
                    System.out.println(fs.name() + " ----- " + fs.supportsFileAttributeView(BasicFileAttributeView.class));
                });
    }

    @Test
    public void fileStore() throws IOException, URISyntaxException {
        boolean supported = Files.getFileStore(Paths.get(this.getClass().getResource("ch02.txt").toURI())).supportsFileAttributeView("basic");
        System.out.println(supported);
    }

    @Test
    public void basicFileAttrs() throws URISyntaxException, IOException {
        BasicFileAttributes attrs = Files.readAttributes(Paths.get(this.getClass().getResource("ch02.txt").toURI()), BasicFileAttributes.class);
        System.out.println("File size: " + attrs.size());
        System.out.println("File creation time: " + attrs.creationTime());
        System.out.println("File last access time: " + attrs.lastAccessTime());
        System.out.println("File last modified time: " + attrs.lastModifiedTime());
        System.out.println("is directory? " + attrs.isDirectory());
        System.out.println("is regular? " + attrs.isRegularFile());
        System.out.println("is symbolic link? " + attrs.isSymbolicLink());
        System.out.println("is other? " + attrs.isOther());
    }

    @Test
    public void basicAttrSize() throws URISyntaxException, IOException {
        long size = (long) Files.getAttribute(Paths.get(this.getClass().getResource("ch02.txt").toURI()), "basic:size", LinkOption.NOFOLLOW_LINKS);
        System.out.println("size: " + size);
    }

    private Path path() throws URISyntaxException {
        return Paths.get(this.getClass().getResource("ch02.txt").toURI());
    }

    @Test
    public void setTime() throws URISyntaxException, IOException {
        FileTime now = FileTime.fromMillis(System.currentTimeMillis());
        Files.getFileAttributeView(path(), BasicFileAttributeView.class).setTimes(now, now, now);
    }

    @Test
    public void setLastModifiedTime() throws URISyntaxException, IOException {
        System.out.println(LocalDateTime.ofInstant(FileTime.fromMillis(System.currentTimeMillis()).toInstant(), ZoneId.systemDefault()));
        Files.setLastModifiedTime(path(), FileTime.fromMillis(System.currentTimeMillis()));
    }

    @Test
    public void parentFileSize() throws URISyntaxException, IOException {
        long size = (long) Files.getAttribute(path(), "size", LinkOption.NOFOLLOW_LINKS);
        System.out.println("size: " + size);
    }

    @Test
    public void root() throws URISyntaxException {
        System.out.println(path().getRoot());
    }

    @Test
    public void fileStoresInfo() throws IOException {
        FileSystem fileSystem = FileSystems.getDefault();
        for (FileStore fs : fileSystem.getFileStores()) {
            System.out.println("store name: " + fs.name());
            System.out.println("store type: " + fs.type());
            System.out.println("total space: " + fs.getTotalSpace() / 1024);
            System.out.println("used space: " + (fs.getTotalSpace() - fs.getUsableSpace()) / 1024);
            System.out.println("available space: " + fs.getUsableSpace() / 1024);
            System.out.println("is read only? " + fs.isReadOnly());
            System.out.println("support user-defined attrs? " + fs.supportsFileAttributeView(UserDefinedFileAttributeView.class));
            System.out.println("------------------------");
        }
    }

    @Test
    public void rootDirectories() {
        FileSystems.getDefault().getRootDirectories()
                .forEach(System.out::println);
    }

    @Test
    public void directoryStream() throws IOException {
        Files.newDirectoryStream(Paths.get("D:/"))
                .forEach(System.out::println);
    }

    @Test
    public void globPattern() throws URISyntaxException, IOException {
        Files.newDirectoryStream(path().getParent(), ".{txt}")
                .forEach(System.out::println);
    }
}
