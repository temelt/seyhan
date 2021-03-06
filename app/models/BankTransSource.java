/**
* Copyright (c) 2015 Mustafa DUMLUPINAR, mdumlupinar@gmail.com
*
* This file is part of seyhan project.
*
* seyhan is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
package models;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import play.data.validation.Constraints;
import play.i18n.Messages;
import utils.ModelHelper;
import enums.Right;
import enums.TransType;

@Entity
/**
 * @author mdpinar
*/
public class BankTransSource extends BaseModel {

	private static final long serialVersionUID = 1L;
	private static final Right RIGHT = Right.BANK_ISLEM_KAYNAKLARI;

	@Constraints.Required
	@Constraints.MinLength(3)
	@Constraints.MaxLength(30)
	public String name;

	public Right suitableRight;

	public Boolean isActive = Boolean.TRUE;

	public static Map<String, String> options(Right suitableRight) {
		return ModelHelper.options(RIGHT, suitableRight);
	}

	public static List<BankTransSource> page() {
		return ModelHelper.page(RIGHT, "name");
	}

	public static BankTransSource findById(Integer id) {
		return ModelHelper.findById(RIGHT, id);
	}

	public static boolean isUsedForElse(String field, Object value, Integer id) {
		return ModelHelper.isUsedForElse(RIGHT, field, value, id);
	}

	public static Map<String, String> crossOptions(String direction) {
		if (direction != null) {
			TransType tt = null;
			try {
				tt = TransType.valueOf(direction);
			} catch (Exception e) {
				;
			}
			if (tt != null) {
				if (tt.equals(TransType.Debt)) return options(Right.BANK_HESABA_PARA_GIRISI);
				if (tt.equals(TransType.Credit)) return options(Right.BANK_HESAPTAN_PARA_CIKISI);
			}
		}
		return options(null);
	}

	@Override
	public Right getAuditRight() {
		return RIGHT;
	}

	@Override
	public String getAuditDescription() {
		return Messages.get("audit.name") + this.name;
	}

	@Override
	public String toString() {
		return name;
	}

}
