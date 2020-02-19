package cs360db.model;

/**
 *
 * @author Tasos198
 */
public class User {

    private Civilian civilian = null;
    private Merchant merchant = null;
    private Company company = null;

    public User(String id, String name, String type) {
        if (type.contains("civilian")) {
            civilian = new Civilian(id, name, type);
        } else if (type.contains("merchant")) {
            merchant = new Merchant(id, name, type);
        } else {
            company = new Company(id, name);
        }
    }

    public User(Civilian civilian) {
        this.civilian = civilian;
    }

    public User(Merchant merchant) {
        this.merchant = merchant;
    }

    public User(Company company) {
        this.company = company;
    }

    public User(Merchant merchant, Company company) {
        this.merchant = merchant;
        this.company = company;
    }

    public User(Civilian civilian, Company company) {
        this.civilian = civilian;
        this.company = company;
    }

    public boolean isEmployeeMerchant() {
        return company != null && merchant != null;
    }

    public boolean isEmployeeCivilian() {
        return company != null && civilian != null;
    }

    public boolean isCompany() {
        return company != null && merchant == null && civilian == null;
    }

    public boolean isMerchant() {
        return company == null && merchant != null && civilian == null;
    }

    public boolean isCivilian() {
        return company == null && merchant == null && civilian != null;
    }

    public Company getCompany() {
        return this.company;
    }

    public Civilian getCivilian() {
        assert (isCivilian());
        return civilian;
    }

    public Merchant getMerchant() {
        assert (isMerchant());
        return merchant;
    }

    public Civilian getEmployeeCivilian() {
        assert (isEmployeeCivilian());
        return this.civilian;
    }

    public Merchant getEmployeeMerchant() {
        assert (isEmployeeMerchant());
        return this.merchant;
    }

    public boolean checkFields() {
        return isCivilian() || isCompany() || isEmployeeCivilian()
                || isEmployeeMerchant() || isMerchant();
    }

    public boolean isValidEmployee() {
        assert (isCivilian() || isMerchant());
        boolean state;
        if (isCivilian()) {
            state = civilian.isValidEmployee();
        } else {
            state = merchant.isValidEmployee();
        }
        return state;
    }
}
