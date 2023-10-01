package es.exmaster.expass.password;

import java.util.Objects;

import es.exmaster.expass.common.Strength;

public class Password implements Comparable<Password> {
	private String user;
	private String site;
	private String password;
	private Strength strength;
	
	public Password(String user, String site, String password) {
		this.user = user;
		this.site = site;
		this.password = password;
		this.strength = isStrong(password);
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSite() {
		return this.site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public Strength getStrength() {
		return this.strength;
	}
	
	public void setStrength(Strength strength) {
		this.strength = strength;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, site, strength, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Password other = (Password) obj;
		return Objects.equals(password, other.password) && Objects.equals(site, other.site)
				&& strength == other.strength && Objects.equals(user, other.user);
	}

	@Override
	public int compareTo(Password o) {
		// TODO Auto-generated method stub
		return this.strength.compareTo(o.getStrength());
	}
	
	@Override
	public String toString() {
		return "Password [user=" + user + ", site=" + site + ", password=" + password + ", strength=" + strength + "]";
	}
        
        /**
        * @param password the password you want to check the strength of
        * @return Strength type indicating password strength
        */
	public static Strength isStrong(String password) {
		Strength res = null;
        if (password.length() >= 8 && containsUppercase(password) && containsLowercase(password)
                && containsNumber(password) && containsSpecialChar(password)) {
            res = Strength.FUERTE; // Fuerte
        } else if (password.length() >= 6 && (containsUppercase(password) || containsLowercase(password)
                || containsNumber(password) || containsSpecialChar(password))) {
        	res = Strength.NORMAL; // Media
        } else if (password.length() >= 4) {
        	res = Strength.DEBIL; // Débil
        } else {
			res = Strength.TERRIBLE; // Terrible
		}
        return res;
    }
    
    private static boolean containsUppercase(String password) {
        return !password.equals(password.toLowerCase());
    }
    
    private static boolean containsLowercase(String password) {
        return !password.equals(password.toUpperCase());
    }
    
    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }
    
    private static boolean containsSpecialChar(String password) {
        return !password.matches("[A-Za-z0-9 ]*");
    }
}
