package utils;

public enum HeaderMenuItem {

    HOME("//a[@href='/home']"),
    ABOUT("//a[@href='/about']"),
    LOGIN("//a[@href='/login']"),
    CONTACTS("//a[@href='/contacts']"),
    ADD("//a[@href='/add']"),
    SIGN_OUT("//button[text()='Sign Out");

    private final String locator;

    HeaderMenuItem(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
