package com.example.pet_care;

import android.provider.BaseColumns;

public final class UserContract {

    private UserContract() {

    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_FULL_NAME = "full_name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_CONTACT_NUMBER = "contact_number";
        public static final String COLUMN_NAME_RESIDENTIAL_ADDRESS = "residential_address";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_AGREED_TO_TERMS = "agreed_to_terms";
    }

    public static class PetEntry implements BaseColumns {
        public static final String TABLE_NAME = "pets";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PET_NAME = "pet_name";
        public static final String COLUMN_NAME_PET_TYPE = "pet_type";
        public static final String COLUMN_NAME_PET_SEX= "pet_sex";
        public static final String COLUMN_NAME_PET_AGE = "pet_age";
        public static final String COLUMN_NAME_PET_NOTE = "pet_note";
        public static final String COLUMN_NAME_OWNER_EMAIL = "owner_email";
    }

    public static class CareGiverEntry implements BaseColumns {
        public static final String TABLE_NAME = "caregivers";
        public static final String COLUMN_NAME_FULL_NAME = "full_name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_CONTACT_NUMBER = "contact_number";
        public static final String COLUMN_NAME_RESIDENTIAL_ADDRESS = "resi" +
                "dential_address";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_AGREED_TO_TERMS = "agreed_to_terms";
    }
    public static class BookedPetsEntry implements BaseColumns {
        public static final String TABLE_NAME = "booked_pets";
        public static final String COLUMN_NAME_BOOKING_ID = "booking_id";
        public static final String COLUMN_NAME_PET_ID = "pet_id";
        public static final String COLUMN_NAME_CUSTOMER_EMAIL = "customer_email";
    }

}

