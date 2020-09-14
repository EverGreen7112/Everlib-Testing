package com.evergreen.fertilizer.shuffleboard.constants;

import com.evergreen.fertilizer.shuffleboard.handlers.Explorer;

import edu.wpi.first.wpilibj.Preferences;

/**
 * A representation of a shuffleboard constant - set with a value at RobotInit,
 * and tuned by drivers and users in the shuffleboard.
 * 
 * @author Atai Ambus
 */
public abstract class Constant {

    /**A name for this constant (the lastpart of its path) */
    private String m_name;

    /**The "folder" this constant is in*/
    private String m_folder;

    /**
     * Construct a super {@link Constant} object with input name, and adds it at the current
     * {@link DashboardConstants} working directory. 
     * <p>
     * Trying to add a constant at an already used path will cause an {@link IllegalArgumentException}.
     * 
     * @param name - The constant's name 
     * @param value - The constant's value (parsed to string for logging purposes)
     */
    public Constant(String name, Object value) {
        m_name = name;
        m_folder = DashboardConstants.getInstance().pwd()
            .substring(1); //Remove root / - incompatiable with suffleboard.

        if (wasAdded()) {
            System.out.println(String.format(
                "Tried to add %s constant \"%s\" at %s, but a constant already"
                + " exists in that path!", 
                getType(), m_name, m_folder));
        } 
        
        else {
            addToDashboard(value);
            System.out.println(String.format("Added %s constant \"%s\" at %s - %s",
                getType(), m_name, m_folder, value.toString()));

            DashboardConstants.getInstance().addConstant(this);
        }

    }

    /**
     * @return the path to the current constanto on the shuffleboard (under Preferences).
     */
    public String getPath() {
        if (m_folder.equals("")) {
            return m_name;
        } else {
            return m_folder + "/" + m_name;
        }
    }

    /**
     * Relocates the constant to the given path
     * (This also renames the constant. if you want to move it)
     * @param path - the constant's new path
     */
    public void setPath(String path) {
        remove();
        m_folder = path.substring(1, path.lastIndexOf('/'));
        m_name = path.substring(path.lastIndexOf('/') + 1, path.length());
        addToDashboard();
    }


    /**
     * Moves the constant to a folder, with respect to standart path syntax (., .. and /)
     * @param folder, 
     */
    public void move(String folder) {
        remove();
        
        Explorer explorer = new Explorer("Temporary " + m_name + " Move Explorer", m_folder);
        explorer.cd(folder);

        m_folder = explorer.pwd().substring(1);
        
        addToDashboard();
    }

    /**
     * Renames the constant.
     * @param newName - The new name
     */
    public void rename(String newName) {
        remove();
        m_name = newName;
        addToDashboard();   
    }

    
    public abstract void addToDashboard();
    protected abstract void addToDashboard(Object value);


    public void reset() {
        addToDashboard();
    }

    public void remove() {
        if (wasAdded()) {
            Preferences.getInstance().remove(getPath());
            System.out.println(String.format("Removed Constant \"%s\" under %s", m_name, m_folder));
        } else {
            System.out.println(String.format(
                "Tried to remove constant \"%s\" from %s, but it was never added to the"
                + " Shuffleboard in the first place! ", 
                m_name, m_folder));
        }
    }

    public boolean wasAdded() {
        return Preferences.getInstance().containsKey(getPath());
    }

    public String getName() {
        return m_name;
    }

    public abstract String getType();
}
