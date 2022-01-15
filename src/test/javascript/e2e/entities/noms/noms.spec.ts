/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import NomsComponentsPage, { NomsDeleteDialog } from './noms.page-object';
import NomsUpdatePage from './noms-update.page-object';
import NomsDetailsPage from './noms-details.page-object';

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

describe('Noms e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: NomsUpdatePage;
  let detailsPage: NomsDetailsPage;
  let listPage: NomsComponentsPage;
  let deleteDialog: NomsDeleteDialog;
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

  it('should load Noms', async () => {
    await navBarPage.getEntityPage('noms');
    listPage = new NomsComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Noms page', async () => {
      await listPage.createButton.click();
      updatePage = new NomsUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/clarisseApp.noms.home.createOrEditLabel/);
    });

    it('should create and save Noms', async () => {
      await updatePage.qualiteInput.sendKeys('qualite');

      await updatePage.nomUsuelInput.sendKeys('nomUsuel');

      await updatePage.prenomUsuelInput.sendKeys('prenomUsuel');

      await updatePage.nomPatronymiqueInput.sendKeys('nomPatronymique');

      await updatePage.prenomCivilInput.sendKeys('prenomCivil');

      await updatePage.nomMaritalInput.sendKeys('nomMarital');

      await updatePage.nomDistinctifInput.sendKeys('nomDistinctif');

      await updatePage.nomMajusculeInput.sendKeys('nomMajuscule');

      await updatePage.nomTechniqueInput.sendKeys('nomTechnique');

      await updatePage.feminisationInput.sendKeys('feminisation');

      await updatePage.dateDebutInput.sendKeys('2001-01-01');

      await updatePage.dateFinInput.sendKeys('2001-01-01');

      // await selectLastOption(updatePage.personneSelect);

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

        deleteDialog = new NomsDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/clarisseApp.noms.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Noms page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new NomsDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Noms page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.qualiteInput.clear();
        await updatePage.qualiteInput.sendKeys('modified');

        await updatePage.nomUsuelInput.clear();
        await updatePage.nomUsuelInput.sendKeys('modified');

        await updatePage.prenomUsuelInput.clear();
        await updatePage.prenomUsuelInput.sendKeys('modified');

        await updatePage.nomPatronymiqueInput.clear();
        await updatePage.nomPatronymiqueInput.sendKeys('modified');

        await updatePage.prenomCivilInput.clear();
        await updatePage.prenomCivilInput.sendKeys('modified');

        await updatePage.nomMaritalInput.clear();
        await updatePage.nomMaritalInput.sendKeys('modified');

        await updatePage.nomDistinctifInput.clear();
        await updatePage.nomDistinctifInput.sendKeys('modified');

        await updatePage.nomMajusculeInput.clear();
        await updatePage.nomMajusculeInput.sendKeys('modified');

        await updatePage.nomTechniqueInput.clear();
        await updatePage.nomTechniqueInput.sendKeys('modified');

        await updatePage.feminisationInput.clear();
        await updatePage.feminisationInput.sendKeys('modified');

        await updatePage.dateDebutInput.clear();
        await updatePage.dateDebutInput.sendKeys('2019-01-01');

        await updatePage.dateFinInput.clear();
        await updatePage.dateFinInput.sendKeys('2019-01-01');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
