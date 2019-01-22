package app.qrme.core.utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJaniko on 10/26/2016.
 */
public class Constants {

    public static final class UploadHelpers {
        private static final String HOME = String.format("%s%s", System.getProperty("user.home"), File.separator);
        public static final String UPLOADS = String.format("%s%s%s", HOME, "uploads", File.separator);
        public static final String USER_IMG = String.format("%s%s", UPLOADS, "user");
    }

    public static final String[] VALID_EXTENSIONS = {"png", "jpg", "jpeg"};

    public static final String[] VALID_OBJECT_NAMES = {
            "USER_IMG",
            "SLIDER_IMG",
            "POST_IMG"
    };

    public static final class ControllerCodes {
        public static final String STRING_EMPTY = "";
        public static final String PAGE_NUMBER = "pageNumber";
        public static final String PAGE_NUMBER_DEFAULT_VALUE = "0";
        public static final String PAGE_SIZE_DEFAULT_VALUE = "10";
        public static final String IS_ASCENDING_DEFAULT_VALUE = "true";

        public static final String SLASH = "/";
        public static final String LIST = "list";
        public static final String LAYOUT = "layout";
        public static final String PUT = "put";
        public static final String DELETE = "delete";
        public static final String UPDATE = "update";
        public static final String KEY = "key";
        public static final String VALUE = "value";
    }

    public static final class ErrorCodes {
        public class ErrorResponse {
            public static final String ACCESS_IS_DENIED = "access_is_denied";
            public static final String UNKNOWN = "unknown";
            public static final String DUPLICATE_RECORD = "DUPLICATE_RECORD";
            public static final String RECORD_IS_USED_IN_OTHER_TABLES = "RECORD_IS_USED_IN_OTHER_TABLES";
            public static final String PERSISTENCE_EXCEPTION = "javax.persistence.PersistenceException";
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static final class CountryNames {
        private String en;
        private String ge;
        private String ru;

    }

    @Getter
    public static class CountryData {
        private List<CountryNames> countryNamesList = new ArrayList<>(54);

        {
            countryNamesList.add(new CountryNames("Tbilisi", "თბილისი", "Тбилиси"));
            countryNamesList.add(new CountryNames("Abasha", "აბაშა", "Абаша"));
            countryNamesList.add(new CountryNames("Ambrolauri", "ამბროლაური", "Амбролаури"));
            countryNamesList.add(new CountryNames("Akhalkalaki", "ახალქალაქი", "Ахалкалаки"));
            countryNamesList.add(new CountryNames("Akhaltsikhe", "ახალციხე", "Ахалцихе"));
            countryNamesList.add(new CountryNames("Akhmeta", "ახმეტა", "Ахмета"));
            countryNamesList.add(new CountryNames("Batumi", "ბათუმი", "Батуми"));
            countryNamesList.add(new CountryNames("Baghdati", "ბაღდათი", "Багдати"));
            countryNamesList.add(new CountryNames("Bolnisi", "ბოლნისი", "Болниси"));
            countryNamesList.add(new CountryNames("Borjomi", "ბორჯომი", "Боржоми"));
            countryNamesList.add(new CountryNames("Gardabani", "გარდაბანი", "Гардабани"));
            countryNamesList.add(new CountryNames("Gori", "გორი", "Гори"));
            countryNamesList.add(new CountryNames("Gurjaani", "გურჯაანი", "Гурджаани"));
            countryNamesList.add(new CountryNames("Dedoplistskaro", "დედოფლისწყარო", "Дедоплисцкаро"));
            countryNamesList.add(new CountryNames("Dmanisi", "დმანისი", "Дманиси"));
            countryNamesList.add(new CountryNames("Dusheti", "დუშეთი", "Душети"));
            countryNamesList.add(new CountryNames("Vale", "ვალე", "Вале"));
            countryNamesList.add(new CountryNames("Vani", "ვანი", "Вани"));
            countryNamesList.add(new CountryNames("Zestaponi", "ზესტაფონი", "Зестафони"));
            countryNamesList.add(new CountryNames("Zugdidi", "ზუგდიდი", "Зугдиди"));
            countryNamesList.add(new CountryNames("Tetritskaro", "თეთრიწყარო", "Тетрицкаро"));
            countryNamesList.add(new CountryNames("Telavi", "თელავი", "Телави"));
            countryNamesList.add(new CountryNames("Terjola", "თერჯოლა", "Терджола"));
            countryNamesList.add(new CountryNames("Kaspi", "კასპი", "Каспи"));
            countryNamesList.add(new CountryNames("Lagodekhi", "ლაგოდეხი", "Лагодехи"));
            countryNamesList.add(new CountryNames("Lanchkhuti", "ლანჩხუთი", "Ланчхути"));
            countryNamesList.add(new CountryNames("Marneuli", "მარნეული", "Марнеули"));
            countryNamesList.add(new CountryNames("Martvili", "მარტვილი", "Мартвили"));
            countryNamesList.add(new CountryNames("Mtskheta", "მცხეთა", "Мцхета"));
            countryNamesList.add(new CountryNames("Ninotsminda", "ნინოწმინდა", "Ниноцминда"));
            countryNamesList.add(new CountryNames("Ozurgeti", "ოზურგეთი", "Озургети"));
            countryNamesList.add(new CountryNames("Oni", "ონი", "Они"));
            countryNamesList.add(new CountryNames("Rustavi", "რუსთავი", "Рустави"));
            countryNamesList.add(new CountryNames("Sagarejo", "საგარეჯო", "Сагареджо"));
            countryNamesList.add(new CountryNames("Samtredia", "სამტრედია", "Самтредия"));
            countryNamesList.add(new CountryNames("Sachkhere", "საჩხერე", "Сачхере"));
            countryNamesList.add(new CountryNames("Senaki", "სენაკი", "Сенаки"));
            countryNamesList.add(new CountryNames("Sighnaghi", "სიღნაღი", "Сигнахи"));
            countryNamesList.add(new CountryNames("Tkibuli", "ტყიბული", "Ткибули"));
            countryNamesList.add(new CountryNames("Poti", "ფოთი", "Поти"));
            countryNamesList.add(new CountryNames("Kareli", "ქარელი", "Карели"));
            countryNamesList.add(new CountryNames("Kobuleti", "ქობულეთი", "Кобулети"));
            countryNamesList.add(new CountryNames("Kutaisi", "ქუთაისი", "Кутаиси"));
            countryNamesList.add(new CountryNames("Kvareli", "ყვარელი", "Кварели"));
            countryNamesList.add(new CountryNames("Tsageri", "ცაგერი", "Цагери"));
            countryNamesList.add(new CountryNames("Tsalenjikha", "წალენჯიხა", "Цаленджиха"));
            countryNamesList.add(new CountryNames("Tsalka", "წალკა", "Цалка"));
            countryNamesList.add(new CountryNames("Tsnori", "წნორი", "Цнори"));
            countryNamesList.add(new CountryNames("Tskaltubo", "წყალტუბო", "Цхалтубо"));
            countryNamesList.add(new CountryNames("Chiatura", "ჭიათურა", "Чиатура"));
            countryNamesList.add(new CountryNames("Khashuri", "ხაშური", "Хашури"));
            countryNamesList.add(new CountryNames("Khobi", "ხობი", "Хоби"));
            countryNamesList.add(new CountryNames("Khoni", "ხონი", "Хони"));
            countryNamesList.add(new CountryNames("Jvari", "ჯვარი", "Джвари"));
        }
    }

    public enum Sections {
        Charitable_Foundation,
        news,
        promotions,
        cardSales,
        recipes,
        corporateSales,
        qualityMgt,
        adv
    }
}
