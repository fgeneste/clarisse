import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class TypeStructureUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.typeStructure.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  codeTypeStructureInput: ElementFinder = element(by.css('input#type-structure-codeTypeStructure'));

  libelleInput: ElementFinder = element(by.css('input#type-structure-libelle'));

  libelleCourtInput: ElementFinder = element(by.css('input#type-structure-libelleCourt'));

  libellePlurielInput: ElementFinder = element(by.css('input#type-structure-libellePluriel'));

  urlCompleteInput: ElementFinder = element(by.css('input#type-structure-urlComplete'));

  urlSimplifieInput: ElementFinder = element(by.css('input#type-structure-urlSimplifie'));

  ordreInput: ElementFinder = element(by.css('input#type-structure-ordre'));
}
