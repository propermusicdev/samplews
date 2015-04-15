package data.Helpers;

/**
 * Created by Lebel on 06/11/2014.
 */
public class ResponseHelper {

    public String refineOutgoingMessage(String input){
        String refined = input;
        if (input.contains("Full_Artist_Value1")) {
            refined = refined.replace("Full_Artist_Value1", "FullArtist");
        }
        if (input.contains("Full_Title_Value1")){
            refined = refined.replace("Full_Title_Value1", "FullTitle");
        }
        return refined;
    }
}
