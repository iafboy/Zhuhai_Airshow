package com.airshow.server.server;

import com.airshow.server.requestHandle.detail.*;
import io.netty.channel.ChannelHandlerContext;

import com.airshow.server.requestHandle.NoSuchAPIRequestHandle;
import com.airshow.server.requestHandle.RequestHandle;
import org.apache.log4j.Logger;

//处理请求类，将请求进行转发
public class RequestHandleManager {
    private static Logger logger = Logger.getLogger(RequestHandleManager.class);
	//转发请求
	public static void forwardRequest(String uri,String requestStr,ChannelHandlerContext ctx){
        logger.debug("HttpRequest URI: "+uri);
        if("/API/UploadPhoneSerialNumber".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new UploadPhoneSerialNumberHandle(ctx, requestStr);
            requestHandle.handle();
        }

		if("/API/Register".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new RegisterRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/Login".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new LoginRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/SetUserInformation".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new SetUserInformationRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetUserInformation".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetUserInformationRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetAllNews".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetAllNewsRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetNewsDetail".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetNewsDetailRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetAdByType".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetAdByTypeRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetAdDetail".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetAdDetailRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetAllPavilion".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetAllPavilionRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetAllBusinessman".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetAllBusinessmanRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}

        if("/API/GetBusinessmanByPavilion".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetBusinessmanByPavilionRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetBusinessmanDetail".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetBusinessmanDetailRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}	
		
		if("/API/GetExhibitByPavilion".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetExhibitByPavilionRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}

        if("/API/GetExhibitByType".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetExhibitByTypeRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }
		
		if("/API/GetExhibitDetail".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetExhibitDetailRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}		
		
		if("/API/Collect".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new CollectRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}	
		
		if("/API/GetUserCollection".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetUserCollectRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetActivityByType".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetActivityByTypeRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetActivityDetail".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetActivityDetailRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}
		
		if("/API/GetOnlineActivityPicture".toLowerCase().equalsIgnoreCase(uri.trim())){
			RequestHandle requestHandle = new GetOnlineActivityPictureRequestHandle(ctx, requestStr);
			requestHandle.handle();
		}

        if("/API/GetAllDocument".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetAllDocumentRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetDocumentDetail".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetDocumentDetailRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetDocumentIntroduction".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetDocumentIntroductionRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetAllActivityPeople".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetAllActivityPeopleRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetAllActivityPeopleImage".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetAllActivityPeopleImageRequestHandle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetAllActivityPeople2".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetAllActivityPeopleRequest2Handle(ctx, requestStr);
            requestHandle.handle();
        }

        if("/API/GetAllActivityPeopleImage2".toLowerCase().equalsIgnoreCase(uri.trim())){
            RequestHandle requestHandle = new GetAllActivityPeopleImageRequest2Handle(ctx, requestStr);
            requestHandle.handle();
        }

		//用户请求不匹配
        logger.debug("HttpRequest "+uri+" can not find matched handler.Try to use "+NoSuchAPIRequestHandle.class.getName());
		RequestHandle requestHandle = new NoSuchAPIRequestHandle(ctx, requestStr);
		requestHandle.handle();
	}
}