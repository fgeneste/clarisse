import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class NomsUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.noms.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  qualiteInput: ElementFinder = element(by.css('input#noms-qualite'));

  nomUsuelInput: ElementFinder = element(by.css('input#noms-nomUsuel'));

  prenomUsuelInput: ElementFinder = element(by.css('input#noms-prenomUsuel'));

  nomPatronymiqueInput: ElementFinder = element(by.css('input#noms-nomPatronymique'));

  prenomCivilInput: ElementFinder = element(by.css('input#noms-prenomCivil'));

  nomMaritalInput: ElementFinder = element(by.css('input#noms-nomMarital'));

  nomDistinctifInput: ElementFinder = element(by.css('input#noms-nomDistinctif'));

  nomMajusculeInput: ElementFinder = element(by.css('input#noms-nomMajuscule'));

  nomTechniqueInput: ElementFinder = element(by.css('input#noms-nomTechnique'));

  feminisationInput: ElementFinder = element(by.css('input#noms-feminisation'));

  dateDebutInput: ElementFinder = element(by.css('input#noms-dateDebut'));

  dateFinInput: ElementFinder = element(by.css('input#noms-dateFin'));

  personneSelect = element(by.css('select#noms-personne'));
}
