/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import PersonneComponentsPage, { PersonneDeleteDialog } from './personne.page-object';
import PersonneUpdatePage from './personne-update.page-object';
import PersonneDetailsPage from './personne-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden,
} from '../../util/utils';

const expect = chai.expect;

describe('Personne e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: PersonneUpdatePage;
  let detailsPage: PersonneDetailsPage;
  let listPage: PersonneComponentsPage;
  let deleteDialog: PersonneDeleteDialog;
  let beforeRecordsCount = 0;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login(username, password);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load Personnes', async () => {
    await navBarPage.getEntityPage('personne');
    listPage = new PersonneComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Personne page', async () => {
      await listPage.createButton.click();
      updatePage = new PersonneUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/clarisseApp.personne.home.createOrEditLabel/);
    });

    it('should create and save Personnes', async () => {
      await updatePage.matriculeInput.sendKeys('matricule');

      await updatePage.dateDeNaissanceInput.sendKeys('2001-01-01');

      await updatePage.lieuDeNaissanceInput.sendKeys('lieuDeNaissance');

      await updatePage.departementDeNaissanceInput.sendKeys('5');

      await updatePage.dateDeDecesInput.sendKeys('2001-01-01');

      await updatePage.lieuDeDecesInput.sendKeys('lieuDeDeces');

      await updatePage.departementDeDecesInput.sendKeys('5');

      await updatePage.professionInput.sendKeys('profession');

      await updatePage.diplomeInput.sendKeys('diplome');

      await updatePage.decorationInput.sendKeys('decoration');

      await updatePage.rangProtocolaireInput.sendKeys('5');

      await updatePage.cspInseeInput.sendKeys('cspInsee');

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.last());
        await click(deleteButton);

        deleteDialog = new PersonneDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/clarisseApp.personne.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Personne page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new PersonneDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Personne page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.matriculeInput.clear();
        await updatePage.matriculeInput.sendKeys('modified');

        await updatePage.dateDeNaissanceInput.clear();
        await updatePage.dateDeNaissanceInput.sendKeys('2019-01-01');

        await updatePage.lieuDeNaissanceInput.clear();
        await updatePage.lieuDeNaissanceInput.sendKeys('modified');

        await clear(updatePage.departementDeNaissanceInput);
        await updatePage.departementDeNaissanceInput.sendKeys('6');

        await updatePage.dateDeDecesInput.clear();
        await updatePage.dateDeDecesInput.sendKeys('2019-01-01');

        await updatePage.lieuDeDecesInput.clear();
        await updatePage.lieuDeDecesInput.sendKeys('modified');

        await clear(updatePage.departementDeDecesInput);
        await updatePage.departementDeDecesInput.sendKeys('6');

        await updatePage.professionInput.clear();
        await updatePage.professionInput.sendKeys('modified');

        await updatePage.diplomeInput.clear();
        await updatePage.diplomeInput.sendKeys('modified');

        await updatePage.decorationInput.clear();
        await updatePage.decorationInput.sendKeys('modified');

        await clear(updatePage.rangProtocolaireInput);
        await updatePage.rangProtocolaireInput.sendKeys('6');

        await updatePage.cspInseeInput.clear();
        await updatePage.cspInseeInput.sendKeys('modified');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
