/*
 * Copyright 2015 James Devito
 * Copyright 2015 Matthew Fritze
 * Copyright 2015 Ben Hunter
 * Copyright 2015 Ji Hwan Kim
 * Copyright 2015 Edwin Rodriguez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* TODO
 * US05.01.01
 * As a claimant, I want to list all the expense items for a claim, in order of entry,
 * showing for each expense item: the date the expense was incurred, the category,
 * the textual description, amount spent, unit of currency, whether there is a photographic receipt,
 * and incompleteness indicator.
 * 
 */

package ca.ualberta.cmput301w15t13.test;

import ca.ualberta.cmput301w15t13.Activities.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cmput301w15t13.Models.ExpenseItem;
import ca.ualberta.cmput301w15t13.Models.ExpenseItemList;

public class ExpenseItemListTests extends ActivityInstrumentationTestCase2<LoginActivity> {
  public ExpenseItemListTests() {
    super(LoginActivity.class);
  }
  
  @Override
  protected void setUp() throws Exception{
	  super.setUp();
  }
  // TODO project 5 
}
