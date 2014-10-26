package kr.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.utils.dto.ValidationDto;

public class ValidationUtils {
	/**
	 * 이메일 체크
	 * @param email
	 * @return
	 */
	public static ValidationDto isEmail(String email) {
		ValidationDto validationDto = new ValidationDto();
		Pattern pattern = null;
		Matcher matcher = null;
		try{
			validationDto.setResult(false);
			validationDto.setCode(100);
			validationDto.setMessage("잘못된 이메일 형식입니다.");
			if(ObjectUtils.isEmpty(email)) {
				throw new Exception("이메일을 입력하세요");
			}
			pattern = Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");
			matcher = pattern.matcher(email);
			if (matcher.find()) {
				validationDto.setResult(true);
				validationDto.setCode(0);
				validationDto.setMessage(email);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			validationDto.setResult(false);
			validationDto.setCode(ex.hashCode());
			validationDto.setMessage(ex.getMessage());
		} finally {
			pattern = null;
			matcher = null;
		}
		return validationDto;
	}

	/**
	 * 전화번호 체크
	 * @param phone
	 * @return
	 */
	public static ValidationDto isPhone(String phone) {
		ValidationDto validationDto = new ValidationDto();
		Pattern pattern = null;
		Matcher matcher = null;
		try{
			validationDto.setResult(false);
			validationDto.setCode(100);
			validationDto.setMessage("잘못된 전화번호 형식입니다.");
			if(ObjectUtils.isEmpty(phone)) {
				throw new Exception("전화번호를 입력하세요!");
			}
			phone = NumberUtils.fmtDigit(phone);
			pattern = Pattern.compile("(^0[1-9][0-9]?)([1-9][0-9]{2,3})([0-9]{4})$");
			matcher = pattern.matcher(phone);
			if (matcher.find()) {
				validationDto.setResult(true);
				validationDto.setCode(0);
				validationDto.setMessage(phone);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			validationDto.setResult(false);
			validationDto.setCode(ex.hashCode());
			validationDto.setMessage(ex.getMessage());
		} finally {
			pattern = null;
			matcher = null;
		}
		return validationDto;
	}

	public static ValidationDto isID(String id) {
		ValidationDto validationDto = new ValidationDto();
		Pattern pattern = null;
		Matcher matcher = null;
		boolean isAlpha = false;
		boolean isNumber = false;
		try{
			validationDto.setResult(false);
			validationDto.setCode(100);
			validationDto.setMessage("잘못된 아이디 형식입니다.(4자리 이상 첫글자는 영문, 영문/숫자 혼용)");

			if(ObjectUtils.isEmpty(id)) {
				throw new Exception("아이디를 입력하세요");
			}
			if (id.length() > 4) {
				throw new Exception("아이디는 4글자 이상 가능합니다.");
			}
			pattern = Pattern.compile("[^a-zA-Z0-9]");
			matcher = pattern.matcher(id);
			if (matcher.find()) {
				throw new Exception("아이디는 영문, 숫자만 가능합니다.");
			}
			pattern = null;
			matcher = null;
			pattern = Pattern.compile("[a-zA-Z]");
			matcher = pattern.matcher(id);
			isAlpha = matcher.find();

			pattern = null;
			matcher = null;
			pattern = Pattern.compile("[0-9]");
			matcher = pattern.matcher(id);
			isNumber = matcher.find();
			if (!isAlpha && !isNumber) {
				throw new Exception("아이디는 숫자와 영문자를 혼용하여야 합니다.");
			}
			pattern = Pattern.compile("^[a-zA-Z]+[0-9]+");
			matcher = pattern.matcher(id);
			if (matcher.find()) {
				validationDto.setResult(true);
				validationDto.setCode(0);
				validationDto.setMessage(id);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			validationDto.setResult(false);
			validationDto.setCode(ex.hashCode());
			validationDto.setMessage(ex.getMessage());
		} finally {
			pattern = null;
			matcher = null;
		}
		return validationDto;
	}


	/**
	 * 비밀번호 체크
	 * @param password	비밀번호
	 * @param filter	비밀번호 불가 문자열
	 * @return
	 */
	public static ValidationDto isPassword(String password, String filter) {
		ValidationDto validationDto = new ValidationDto();
		Pattern pattern = null;
		Matcher matcher = null;
		boolean isAlpha = false;
		boolean isNumber = false;
		try{
			if(ObjectUtils.isEmpty(password)) {
				throw new Exception("비밀번호를 입력하세요");
			}
			validationDto.setResult(true);
			validationDto.setCode(0);
			validationDto.setMessage(password);

			pattern = Pattern.compile("[^a-zA-Z0-9]");
			matcher = pattern.matcher(password);
			if (matcher.find()) {
				throw new Exception("영문, 숫자만 가능합니다.");
			}
			pattern = Pattern.compile("^[a-zA-Z0-9]{8,30}$");
			matcher = pattern.matcher(password);
			if (matcher.find()) {
				throw new Exception("비밀번호는 숫자와 영문자 조합으로 8~30자리를 사용해야 합니다.");
			}
			pattern = null;
			matcher = null;
			pattern = Pattern.compile("(\\w)\\1\\1\\1");
			matcher = pattern.matcher(password);
			if (matcher.find()) {
				throw new Exception("비밀번호에 같은 문자를 4번 이상 사용하실 수 없습니다.");
			}
			pattern = null;
			matcher = null;
			pattern = Pattern.compile("[a-zA-Z]");
			matcher = pattern.matcher(password);
			isAlpha = matcher.find();

			pattern = null;
			matcher = null;
			pattern = Pattern.compile("[0-9]");
			matcher = pattern.matcher(password);
			isNumber = matcher.find();
			if (!isAlpha && !isNumber) {
				throw new Exception("비밀번호는 숫자와 영문자를 혼용하여야 합니다.");
			}
			if (!ObjectUtils.isEmpty(filter)) {
				pattern = null;
				matcher = null;
				pattern = Pattern.compile(filter);
				matcher = pattern.matcher(password);
				if (matcher.find()) {
					throw new Exception("비밀번호는 사용하실 수 없습니다.");
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			validationDto.setResult(false);
			validationDto.setCode(ex.hashCode());
			validationDto.setMessage(ex.getMessage());
		} finally {
			pattern = null;
			matcher = null;
		}
		return validationDto;
	}
}