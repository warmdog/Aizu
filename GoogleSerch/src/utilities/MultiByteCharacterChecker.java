package utilities;

import java.util.regex.Pattern;

public class MultiByteCharacterChecker {
	/**
	 * 指定した文字列が半角文字のみか判断する
	 *
	 * @param source 対象文字列
	 * @return trueなら半角文字のみ 空の場合は常にtrueとなる
	 */
	public boolean isHankakuOnly(String source) {
		if (source == null || source.equals("")) {
			return true;
		}
		String regText = "[ -~｡-ﾟ]+";
		Pattern pattern = Pattern.compile(regText);
		return pattern.matcher(source).matches();
	}

	/**
	 * 指定した文字列が全角文字のみか判断する
	 *
	 * @param source 対象文字列
	 * @return trueなら全角文字のみ 空の場合は常にtrueとなる
	 */
	public boolean isZenkakuOnly(String source) {
		if (source == null || source.equals("")) {
			return true;
		} 
		else  {
			String regText = "[^ -~｡-ﾟ]+";
			Pattern pattern = Pattern.compile(regText);
			return pattern.matcher(source).matches();
		}
	}
}
