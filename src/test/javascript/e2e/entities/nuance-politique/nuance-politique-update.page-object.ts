import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class NuancePolitiqueUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.nuancePolitique.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  codeNuancePolitiqueInput: ElementFinder = element(by.css('input#nuance-politique-codeNuancePolitique'));

  libelleInput: ElementFinder = element(by.css('input#nuance-politique-libelle'));
}
