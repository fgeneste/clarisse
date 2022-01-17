/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import TypeStructureComponentsPage, { TypeStructureDeleteDialog } from './type-structure.page-object';
import TypeStructureUpdatePage from './type-structure-update.page-object';
import TypeStructureDetailsPage from './type-structure-details.page-object';

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

describe('TypeStructure e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: TypeStructureUpdatePage;
  let detailsPage: TypeStructureDetailsPage;
  let listPage: TypeStructureComponentsPage;
  let deleteDialog: TypeStructureDeleteDialog;
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

  it('should load TypeStructures', async () => {
    await navBarPage.getEntityPage('type-structure');
    listPage = new TypeStructureComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create TypeStructure page', async () => {
      await listPage.createButton.click();
      updatePage = new TypeStructureUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/clarisseApp.typeStructure.home.createOrEditLabel/);
    });

    it('should create and save TypeStructures', async () => {
      await updatePage.codeTypeStructureInput.sendKeys('codeTypeStructure');

      await updatePage.libelleInput.sendKeys('libelle');

      await updatePage.libelleCourtInput.sendKeys('libelleCourt');

      await updatePage.libellePlurielInput.sendKeys('libellePluriel');

      await updatePage.urlCompleteInput.sendKeys('urlComplete');

      await updatePage.urlSimplifieInput.sendKeys('urlSimplifie');

      await updatePage.ordreInput.sendKeys('5');

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

        deleteDialog = new TypeStructureDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/clarisseApp.typeStructure.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details TypeStructure page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new TypeStructureDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit TypeStructure page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.codeTypeStructureInput.clear();
        await updatePage.codeTypeStructureInput.sendKeys('modified');

        await updatePage.libelleInput.clear();
        await updatePage.libelleInput.sendKeys('modified');

        await updatePage.libelleCourtInput.clear();
        await updatePage.libelleCourtInput.sendKeys('modified');

        await updatePage.libellePlurielInput.clear();
        await updatePage.libellePlurielInput.sendKeys('modified');

        await updatePage.urlCompleteInput.clear();
        await updatePage.urlCompleteInput.sendKeys('modified');

        await updatePage.urlSimplifieInput.clear();
        await updatePage.urlSimplifieInput.sendKeys('modified');

        await clear(updatePage.ordreInput);
        await updatePage.ordreInput.sendKeys('6');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
