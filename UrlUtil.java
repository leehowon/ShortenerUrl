public class UrlUtil
{
    private static final String[] ELEMENTS = {
        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
        "p","q","r","s","t","u","v","w","x","y","z","1","2","3","4",
        "5","6","7","8","9","0","A","B","C","D","E","F","G","H","I",
        "J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X",
        "Y","Z"
    };
    
    private static final long BASE62 = 62;
    
    public static String generateShortUrl( long key )
    {
        StringBuffer sb = new StringBuffer();
        
        while( key > 0 )
        {
            int remain = ( int )( key % BASE62 );
            
            sb.append( ELEMENTS[remain] );
            key = key / BASE62;
        }
        
        return sb.reverse().toString();
    }
}
