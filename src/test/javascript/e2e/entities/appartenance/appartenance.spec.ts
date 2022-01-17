/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import AppartenanceComponentsPage, { AppartenanceDeleteDialog } from './appartenance.page-object';
import AppartenanceUpdatePage from './appartenance-update.page-object';
import AppartenanceDetailsPage from './appartenance-details.page-object';

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

describe('Appartenance e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: AppartenanceUpdatePage;
  let detailsPage: AppartenanceDetailsPage;
  let listPage: AppartenanceComponentsPage;
  let deleteDialog: AppartenanceDeleteDialog;
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

  it('should load Appartenances', async () => {
    await navBarPage.getEntityPage('appartenance');
    listPage = new AppartenanceComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Appartenance page', async () => {
      await listPage.createButton.click();
      updatePage = new AppartenanceUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/clarisseApp.appartenance.home.createOrEditLabel/);
    });

    it('should create and save Appartenances', async () => {
      await updatePage.matriculeInput.sendKeys('matricule');

      await updatePage.dateDebutInput.sendKeys('2001-01-01');

      await updatePage.dateFinInput.sendKeys('2001-01-01');

      await updatePage.dateElectionInput.sendKeys('2001-01-01');

      await updatePage.observationInput.sendKeys('observation');

      await updatePage.departementInput.sendKeys('5');

      await updatePage.libelleInput.sendKeys('libelle');

      await updatePage.articleInput.sendKeys('article');

      await updatePage.motifDeDebutInput.sendKeys('motifDeDebut');

      await updatePage.commentaireDeChangementInput.sendKeys('commentaireDeChangement');

      // await selectLastOption(updatePage.typeAppartenanceSelect);
      // await selectLastOption(updatePage.structureSelect);
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

        deleteDialog = new AppartenanceDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/clarisseApp.appartenance.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Appartenance page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new AppartenanceDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Appartenance page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.matriculeInput.clear();
        await updatePage.matriculeInput.sendKeys('modified');

        await updatePage.dateDebutInput.clear();
        await updatePage.dateDebutInput.sendKeys('2019-01-01');

        await updatePage.dateFinInput.clear();
        await updatePage.dateFinInput.sendKeys('2019-01-01');

        await updatePage.dateElectionInput.clear();
        await updatePage.dateElectionInput.sendKeys('2019-01-01');

        await updatePage.observationInput.clear();
        await updatePage.observationInput.sendKeys('modified');

        await clear(updatePage.departementInput);
        await updatePage.departementInput.sendKeys('6');

        await updatePage.libelleInput.clear();
        await updatePage.libelleInput.sendKeys('modified');

        await updatePage.articleInput.clear();
        await updatePage.articleInput.sendKeys('modified');

        await updatePage.motifDeDebutInput.clear();
        await updatePage.motifDeDebutInput.sendKeys('modified');

        await updatePage.commentaireDeChangementInput.clear();
        await updatePage.commentaireDeChangementInput.sendKeys('modified');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
