package gr.arma3.arma.modarchiver.api.v1.util;

import lombok.ToString;
import lombok.experimental.UtilityClass;

/**
 * Size constants. This used to be an enum but having to jump through a getter
 * to get the value got old pretty quick.
 */
@ToString
@UtilityClass
public class Size {
	public static final int KiB = 1 << 10;
	public static final int MiB = 1 << 20;
	public static final int GiB = 1 << 30;
}
