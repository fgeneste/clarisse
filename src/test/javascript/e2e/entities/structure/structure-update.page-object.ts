import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class StructureUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.structure.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  codeStructureInput: ElementFinder = element(by.css('input#structure-codeStructure'));

  dateDeCreationInput: ElementFinder = element(by.css('input#structure-dateDeCreation'));

  dateDeClotureInput: ElementFinder = element(by.css('input#structure-dateDeCloture'));

  codeAmeliInput: ElementFinder = element(by.css('input#structure-codeAmeli'));

  codeSirpasInput: ElementFinder = element(by.css('input#structure-codeSirpas'));

  codeReprographieInput: ElementFinder = element(by.css('input#structure-codeReprographie'));

  articleInput: ElementFinder = element(by.css('input#structure-article'));

  libelleInput: ElementFinder = element(by.css('input#structure-libelle'));

  libelleCourtInput: ElementFinder = element(by.css('input#structure-libelleCourt'));

  libelleLongInput: ElementFinder = element(by.css('input#structure-libelleLong'));

  ordreInput: ElementFinder = element(by.css('input#structure-ordre'));

  publicationSurInternetInput: ElementFinder = element(by.css('input#structure-publicationSurInternet'));

  typeStructureSelect = element(by.css('select#structure-typeStructure'));
}
