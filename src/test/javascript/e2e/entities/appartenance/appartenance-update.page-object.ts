import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class AppartenanceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('clarisseApp.appartenance.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  matriculeInput: ElementFinder = element(by.css('input#appartenance-matricule'));

  dateDebutInput: ElementFinder = element(by.css('input#appartenance-dateDebut'));

  dateFinInput: ElementFinder = element(by.css('input#appartenance-dateFin'));

  dateElectionInput: ElementFinder = element(by.css('input#appartenance-dateElection'));

  observationInput: ElementFinder = element(by.css('input#appartenance-observation'));

  departementInput: ElementFinder = element(by.css('input#appartenance-departement'));

  libelleInput: ElementFinder = element(by.css('input#appartenance-libelle'));

  articleInput: ElementFinder = element(by.css('input#appartenance-article'));

  motifDeDebutInput: ElementFinder = element(by.css('input#appartenance-motifDeDebut'));

  commentaireDeChangementInput: ElementFinder = element(by.css('input#appartenance-commentaireDeChangement'));

  typeAppartenanceSelect = element(by.css('select#appartenance-typeAppartenance'));

  structureSelect = element(by.css('select#appartenance-structure'));

  personneSelect = element(by.css('select#appartenance-personne'));
}
