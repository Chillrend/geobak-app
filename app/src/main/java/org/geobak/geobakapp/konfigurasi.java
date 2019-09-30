package org.geobak.geobakapp;

public class konfigurasi {

    public static final String URL_ADD_USER ="http://192.168.43.47:81/goldenpadLama/tambahUser.php";
    public static final String URL_GET_EMP = "http://192.168.43.47:81/goldenpadLama/tampil.php?username=";
    public static final String URL_UPDATE_EMP = "http://192.168.43.47:81/goldenpadLama/updateUser.php";

    //ADD STORY
    public static final String URL_ADD_STORY="http://192.168.43.47:81/goldenpadLama/tambahStory.php";
    public static final String URL_GET_ALL_STORY = "http://192.168.43.47:81/goldenpadLama/tampilSemuaStory.php";
    public static final String URL_GET_EMP_DESCSTORY = "http://192.168.43.47:81/goldenpad/tampilDescStory.php?story_ID=";
    public static final String URL_GET_EMP_STORY = "http://192.168.43.47:81/goldenpadLama/tampilStory.php?story_ID=";
    public static final String URL_GET_EMP_GENRE_ROMANCE = "http://192.168.43.47:81/goldenpadLama/tampilGenreRomance.php";
    public static final String URL_GET_EMP_GENRE_THRILLER = "http://192.168.43.47:81/goldenpadLama/tampilGenreThriller.php";
    public static final String URL_GET_EMP_GENRE_HORROR = "http://192.168.43.47:81/goldenpadLama/tampilGenreHorror.php";
    public static final String URL_GET_EMP_GENRE_COMEDY = "http://192.168.43.47:81/goldenpadLama/tampilGenreComedy.php";


//    public static final String URL_UPDATE_EMP_STORY = "http://192.168.100.24:81/gatcha/updateStory.php";
//    public static final String URL_DELETE_EMP_STORY = "http://192.168.100.24:81/gatcha/hapusStory.php?ID=";

    public static final String URL_GET_ALL_MY_STORY = "http://192.168.43.47:81/goldenpadLama/tampilSemuaMyStory.php";
    public static final String URL_UPDATE_EMP_MY_STORY = "http://192.168.43.47:81/goldenpadLama/updateMyStory.php";
    public static final String URL_DELETE_EMP_MY_STORY = "http://192.168.43.47:81/goldenpadLama/hapusMyStory.php?story_ID=";

    //VARIABLE VALUE KE PHP USER
//    public static final String KEY_EMP_ID = "ID";
//    public static final String KEY_EMP_USERNAME = "username";
//    public static final String KEY_EMP_PASSWORD = "password";
//    public static final String KEY_EMP_ROLE = "role";


    public static final String URL_GET_ALL_PROFILE = "http://192.168.43.47:81/goldenpadLama/tampilSemuaProfil.php?username=";


    public static final String URL_GET_PROFIL = "http://192.168.43.47:81/goldenpadLama/tampilProfil.php?username=";


    public static final String URL_GET_EMP_EDIT_STORY = "http://192.168.43.47:81/goldenpadLama/tampilEditStory.php?story_ID=";


    public static final String URL_GET_EMP_EDIT_PROFILE = "http://192.168.43.47:81/goldenpadLama/tampilEditProfile.php?ID=";
    public static final String URL_UPDATE_EMP_MY_PROFILE = "http://192.168.43.47:81/goldenpadLama/updateMyProfile.php";


    //VARIABLE VALUE KE PHP USER
    public static final String KEY_EMP_ID = "ID";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String KEY_EMP_USERNAME = "username";
    public static final String KEY_EMP_PASSWORD = "password";
    //    public static final String KEY_EMP_ROLE = "role";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_GENDER = "gender";
    public static final String KEY_EMP_DOB = "DOB";
    public static final String KEY_EMP_IMAGE = "image";


    //VARIABLE VALUE KE PHP STORY
    public static final String KEY_EMP_ID_STORY = "story_ID";
    public static final String KEY_EMP_IMAGE_STORY = "image";
    public static final String KEY_EMP_TITLE_STORY = "title";
    public static final String KEY_EMP_GENRE_STORY = "genre";
    public static final String KEY_EMP_DESC_STORY = "desc";
    public static final String KEY_EMP_STORY = "stories";
    public static final String KEY_EMP_ID_USER = "ID";
    public static final String KEY_EMP_AUTHOR_STORY = "author";
    public static final String KEY_EMP_DATE_STORY = "date";





    //    JSON NYA USER
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "ID";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_ROLE = "role";

    //    public static final String TAG_ROLE = "role";
    public static final String TAG_NAME = "name";
    public static final String TAG_GENDER = "gender";
    public static final String TAG_DOB = "DOB";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_IMAGE = "image";


    //JSON NYA STORY
    public static final String TAG_JSON_ARRAY_STORY="resultS";
    public static final String TAG_ID_STORY = "story_ID";
    public static final String TAG_TITLE_STORY = "title";
    public static final String TAG_GENRE_STORY = "genre";
    public static final String TAG_DESC_STORY = "desc";
    public static final String TAG_STORY = "stories";
    public static final String TAG_AUTHOR_STORY = "author";
    public static final String TAG_DATE_STORY = "date";
    public static final String TAG_ID_USER = "ID";


    //JSON NYA MY STORY
    public static final String TAG_ID_MY_STORY = "story_IDs";
    public static final String TAG_TITLE_MY_STORY = "titles";
    public static final String TAG_GENRE_MY_STORY = "genres";
    public static final String TAG_DESC_MY_STORY = "descs";
    public static final String TAG_MY_STORY = "stories";
    //    public static final String TAG_AUTHOR_MY_STORY = "authors";
    public static final String TAG_DATE_MY_STORY = "dates";
    //    public static final String TAG_MY_ID_USER = "IDs";
    public static final String TAG_IMAGE_MY_STORY = "image";


//    public static final String TAG_DESC = "desc";
//    public static final String TAG_STORY = "story";
//    public static final String TAG_IMAGE = "image";
//    public static final String TAG_DATE = "date";
//    public static final String TAG_IDSTORY = "ID";


    //FINAL USER
//    public static final String EMP_ID = "emp_ID";

    //FINAL STORY
    public static final String EMP_AUTH_STORY = "emp_ID";
    public static final String EMP_ID_STORY = "emp_ID";

}
