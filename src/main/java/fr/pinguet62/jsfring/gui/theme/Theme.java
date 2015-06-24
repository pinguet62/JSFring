package fr.pinguet62.jsfring.gui.theme;

public enum Theme {
    AFTERDARK("afterdark", "Afterdark", "afterdark.png"), AFTERNOON(
            "afternoon", "Afternoon", "afternoon.png"), AFTERWORK("afterwork",
            "Afterwork", "afterwork.png"), ARISTO("aristo", "Aristo",
            "aristo.png"), BLACK_TIE("black-tie", "Black-Tie", "black-tie.png"), BLITZER(
            "blitzer", "Blitzer", "blitzer.png"), BLUESKY("bluesky", "Bluesky",
            "bluesky.png"), BOOTSTRAP("bootstrap", "Bootstrap", "bootstrap.png"), CASABLANCA(
            "casablanca", "Casablanca", "casablanca.png"), CRUZE("cruze",
            "Cruze", "cruze.png"), CUPERTINO("cupertino", "Cupertino",
            "cupertino.png"), DELTA("delta", "Delta", "delta.png"), DARK_HIVE(
            "dark-hive", "Dark-Hive", "dark-hive.png"), DOT_LUV("dot-luv",
            "Dot-Luv", "dot-luv.png"), EGGPLANT("eggplant", "Eggplant",
            "eggplant.png"), EXCITE_BIKE("excite-bike", "Excite-Bike",
            "excite-bike.png"), FLICK("flick", "Flick", "flick.png"), GLASS_X(
            "glass-x", "Glass-X", "glass-x.png"), HOME("home", "Home",
            "home.png"), HOT_SNEAKS("hot-sneaks", "Hot-Sneaks",
            "hot-sneaks.png"), HUMANITY("humanity", "Humanity", "humanity.png"), LE_FROG(
            "le-frog", "Le-Frog", "le-frog.png"), MIDNIGHT("midnight",
            "Midnight", "midnight.png"), MINT_CHOC("mint-choc", "Mint-Choc",
            "mint-choc.png"), OVERCAST("overcast", "Overcast", "overcast.png"), PEPPER_GRINDER(
            "pepper-grinder", "Pepper-Grinder", "pepper-grinder.png"), REDMOND(
            "redmond", "Redmond", "redmond.png"), ROCKET("rocket", "Rocket",
            "rocket.png"), SAM("sam", "Sam", "sam.png"), SMOOTHNESS(
            "smoothness", "Smoothness", "smoothness.png"), SOUTH_STREET(
            "south-street", "South-Street", "south-street.png"), START("start",
            "Start", "start.png"), SUNNY("sunny", "Sunny", "sunny.png"), SWANKY_PURSE(
            "swanky-purse", "Swanky-Purse", "swanky-purse.png"), TRONTASTIC(
            "trontastic", "Trontastic", "trontastic.png"), UI_DARKNESS(
            "ui-darkness", "UI-Darkness", "ui-darkness.png"), UI_LIGHTNESS(
            "ui-lightness", "UI-Lightness", "ui-lightness.png"), VADER("vader",
            "Vader", "vader.png");

    /**
     * Get the {@link Theme} from the {@link #key}.
     *
     * @param key The key.
     * @return The {@link Theme}.
     * @throws IllegalArgumentException Unknown {@link #key}.
     */
    public static Theme fromKey(String key) {
        for (Theme theme : values())
            if (key.equals(theme.getKey()))
                return theme;
        throw new IllegalArgumentException("Unknown key: " + key);
    }

    /** The path of image. */
    private final String image;

    /** The unique key used by PrimeFaces. */
    private final String key;

    private final String name;

    private Theme(String key, String name, String image) {
        this.key = key;
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return key;
    }
}