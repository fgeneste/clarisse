import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class PersonneUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.personne.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  matriculeInput: ElementFinder = element(by.css('input#personne-matricule'));

  dateDeNaissanceInput: ElementFinder = element(by.css('input#personne-dateDeNaissance'));

  lieuDeNaissanceInput: ElementFinder = element(by.css('input#personne-lieuDeNaissance'));

  departementDeNaissanceInput: ElementFinder = element(by.css('input#personne-departementDeNaissance'));

  dateDeDecesInput: ElementFinder = element(by.css('input#personne-dateDeDeces'));

  lieuDeDecesInput: ElementFinder = element(by.css('input#personne-lieuDeDeces'));

  departementDeDecesInput: ElementFinder = element(by.css('input#personne-departementDeDeces'));

  professionInput: ElementFinder = element(by.css('input#personne-profession'));

  diplomeInput: ElementFinder = element(by.css('input#personne-diplome'));

  decorationInput: ElementFinder = element(by.css('input#personne-decoration'));

  rangProtocolaireInput: ElementFinder = element(by.css('input#personne-rangProtocolaire'));

  cspInseeInput: ElementFinder = element(by.css('input#personne-cspInsee'));

  nuancePolitiqueSelect = element(by.css('select#personne-nuancePolitique'));
}
