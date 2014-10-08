package kr.kcp.service.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.kcp.service.common.dao.DefaultDAOFactory;
import kr.kcp.service.mrtong.biz.url.UrlMap;
import kr.kcp.service.system.DefaultMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UrlServlet extends HttpServlet
{
    private static final long serialVersionUID = 4803707115554794189L;
    
    private static final Log logger = LogFactory.getLog( UrlServlet.class );
    
    private static final Map< String, String > URL = new HashMap< String, String >();
    
    private static final String DEFAULT_URL = "http://www.mrtong.co.kr";
    
    private void execute( HttpServletRequest request, HttpServletResponse response, String method ) throws ServletException, IOException
    {
        String shortUrl = request.getRequestURI().replace( "/short/", "" )
                , longUrl = null;
        
        if( shortUrl == null || "".equals(shortUrl) )
        {
            logger.debug( "Short URL Not Found" );
            response.sendRedirect( DEFAULT_URL );
            return;
        }
        
        longUrl = URL.get( shortUrl );
        
        if( longUrl == null || "".equals(longUrl) )
        {
            DefaultMap dMap = new DefaultMap()
                        , urlMap = null;
            
            try
            {
                UrlMap UrlMapDao = ( UrlMap ) DefaultDAOFactory.getDAOFactory().getDAO( UrlMap.class );

                dMap.put( "short_url", shortUrl );
                urlMap = UrlMapDao.selectUrlMap( dMap );
            }
            catch( ServletException e )
            {
                throw e;
            }
            catch( IOException e )
            {
                throw e;
            }
            catch( Exception e )
            {
                logger.error( e.getMessage(), e );
            }
            
            if( urlMap == null )
            {
                logger.debug( "\"" + shortUrl + "\" Url Not Exists." );
                response.sendRedirect( DEFAULT_URL );
                return;
            }

            longUrl = urlMap.getStr( "long_url" );
            URL.put( shortUrl, longUrl );
        }
        
        response.sendRedirect( longUrl );
    }
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        execute( request, response, "GET" );
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        execute( request, response, "POST" );
    }
}
