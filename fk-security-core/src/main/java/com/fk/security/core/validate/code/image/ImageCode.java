/**
 * 
 */
package com.fk.security.core.validate.code.image;

import com.fk.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;



/**
 * @author zhailiang
 *
 */
public class ImageCode extends ValidateCode {

	private static final long serialVersionUID = 3869368955831989858L;
	private BufferedImage image;
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
