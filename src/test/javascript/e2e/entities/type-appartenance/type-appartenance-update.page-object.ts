import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class TypeAppartenanceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.typeAppartenance.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  typeAppartenanceInput: ElementFinder = element(by.css('input#type-appartenance-typeAppartenance'));

  libelleInput: ElementFinder = element(by.css('input#type-appartenance-libelle'));
}
