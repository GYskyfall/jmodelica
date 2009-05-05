
/*
Copyright (C) 2009 Modelon AB

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/** \file CGenerator.java
*  \brief CGenerator class.
*/

package org.jmodelica.codegen;

import java.io.*;
import org.jmodelica.ast.*;

/**
 * A generator class containing a basic set of tags which
 * are not language dependent.
 * 
 * This class is also intended as base class for more specialized generators.
 *
 */
public class CGenerator extends GenericGenerator {

	class DAETag_C_equationResiduals extends DAETag {
		
		public DAETag_C_equationResiduals(AbstractGenerator myGenerator, 
		  FClass fclass) {
			super("C_DAE_equation_residuals","C: equation residuals",
			  myGenerator,fclass);
		}
	
		public void generate(PrintStream genPrinter) {
			int i=0;
			for (FAbstractEquation e : fclass.equations()) {
				e.genResidual_C(i,"    ",genPrinter);				
				i++;
			}
		}
	
	}
	
	class DAETag_C_initialEquationResiduals extends DAETag {
		
		public DAETag_C_initialEquationResiduals(AbstractGenerator myGenerator, 
		  FClass fclass) {
			super("C_DAE_initial_equation_residuals","C: initial equation residuals",
			  myGenerator,fclass);
		}
	
		public void generate(PrintStream genPrinter) {
			int i=0;
			for (FAbstractEquation e : fclass.equations()) {
				e.genResidual_C(i,"    ",genPrinter);				
				i++;
			}
			for (FAbstractEquation e : fclass.initialEquations()) {
				e.genResidual_C(i,"    ",genPrinter);				
				i++;
			}
		}
	
	}

	class DAETag_C_initialGuessEquationResiduals extends DAETag {
		
		public DAETag_C_initialGuessEquationResiduals(AbstractGenerator myGenerator, 
		  FClass fclass) {
			super("C_DAE_initial_guess_equation_residuals","C: initial guess equation residuals",
			  myGenerator,fclass);
		}
	
		public void generate(PrintStream genPrinter) {
			int i=0;
			for (FRealVariable fv : fclass.realVariables()) {
				if (!(fv.fixedAttribute() ||
						(fv.isDifferentiatedVariable() && fv.startAttributeSet()))) {
					fv.genStartAttributeResidual_C(i,"   ",genPrinter);
				}
				i++;
			}
		}
	
	}
	
	class DAETag_C_variableAliases extends DAETag {
		
		public DAETag_C_variableAliases(AbstractGenerator myGenerator, 
		  FClass fclass) {
			super("C_variable_aliases","C: macros for C variable aliases",
			  myGenerator,fclass);
		}
	
		public void generate(PrintStream genPrinter) {
			for (FVariable fv : fclass.independentRealConstants()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_ci+" + 
						fv.independentRealConstantIndex() + "])\n");
			}
			for (FVariable fv : fclass.dependentRealConstants()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_cd+" + 
						fv.dependentRealConstantIndex() + "])\n");
			}
			for (FVariable fv : fclass.independentRealParameters()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_pi+" + 
						fv.independentRealParameterIndex() + "])\n");
			}
			for (FVariable fv : fclass.dependentRealParameters()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_pd+" + 
						fv.dependentRealParameterIndex() + "])\n");
			}
			for (FVariable fv : fclass.derivativeVariables()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_dx+" + 
						fv.derivativeVariableIndex() + "])\n");
			}
			for (FVariable fv : fclass.differentiatedRealVariables()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_x+" + 
						fv.differentiatedRealVariableIndex() + "])\n");
			}
			for (FVariable fv : fclass.realInputs()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_u+" + 
						fv.realInputIndex() + "])\n");
			}
			for (FVariable fv : fclass.algebraicRealVariables()) {
				genPrinter.print("#define " + fv.nameUnderscore());
				genPrinter.print(" ((*(jmi->z))[jmi->offs_w+" + 
						fv.algebraicRealVariableIndex() + "])\n");
			}
			genPrinter.print("#define time ((*(jmi->z))[jmi->offs_t])\n"); 
		}
	}
	
	
	/**
	 * Constructor.
	 * 
	 * @param expPrinter Printer object used to generate code for expressions.
	 * @param escapeCharacter Escape characters used to decode tags.
	 * @param fclass An FClass object used as a basis for the code generation.
	 */
	public CGenerator(Printer expPrinter, char escapeCharacter,
			FClass fclass) {
		super(expPrinter,escapeCharacter, fclass);

		// Create tags			
		AbstractTag tag = null;
		
		tag = new DAETag_C_equationResiduals(this,fclass);
		tagMap.put(tag.getName(),tag);
		tag = new DAETag_C_initialEquationResiduals(this,fclass);
		tagMap.put(tag.getName(),tag);
		tag = new DAETag_C_initialGuessEquationResiduals(this,fclass);
		tagMap.put(tag.getName(),tag);
		tag = new DAETag_C_variableAliases(this,fclass);
		tagMap.put(tag.getName(),tag);		
		
	}

}

