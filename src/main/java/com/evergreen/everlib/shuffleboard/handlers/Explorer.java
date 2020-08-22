package com.evergreen.everlib.shuffleboard.handlers;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import com.evergreen.everlib.shuffleboard.loggables.LoggableData;
import com.evergreen.everlib.shuffleboard.loggables.LoggableObject;
import com.evergreen.everlib.shuffleboard.loggables.LoggableString;

/**
 * Explorer
 */
public class Explorer implements LoggableObject {

    private String m_workingDirectory;
    private String m_name;
    private Stack<String> 
        m_directoryStack,
        m_history;

    public Explorer(String name) {
        m_name = name;
        m_workingDirectory = "";
        m_directoryStack = new Stack<>();
        m_history = new Stack<>();

        m_history.push("/");
    }

    public Explorer(String name, String initDir) {
        this(name);
        cd(initDir);
    }

    public void cd(String path) {
        if (path.startsWith("/")) {
            m_workingDirectory = "";
            path = path.substring(1, path.length());
        }

        for (String folder : path.split("/")) {

            if (folder.equals("..")) {
                m_workingDirectory = 
                    m_workingDirectory.substring(0, pwd().lastIndexOf('/') - 1);
            }

            else if (!folder.equals(".")) {
                
                if (!m_workingDirectory.equals("")) {
                    m_workingDirectory += "/";
                }

                m_workingDirectory += folder;
            }
        }

        m_history.push(pwd());
    }

    public String pwd() {
        return "/" + m_workingDirectory;
    }

    public void pushd(String path) {
        m_directoryStack.push(pwd());
        cd(path);
    }

    public void popd() {
        cd(m_directoryStack.pop());
    }

    @Override
    public String getName() {
        return m_name;
    }

    @Override
    public List<LoggableData> getLoggableData() {
        return List.of(
            new LoggableString("Working Directoty", this::pwd),
            new LoggableString("Directory Stack Top", this::peekd),
            new LoggableString("History", m_history::peek)
        );
    }

    public String peekd() {
        try {
            return m_directoryStack.peek();
        }

        catch (EmptyStackException e) {
            return "<Empty>";
        }
    }

    public void popHistory() {
        m_history.pop();
    }

    public boolean atRoot() {
        return m_workingDirectory == "";
    }
}