/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import StructureComponentsPage, { StructureDeleteDialog } from './structure.page-object';
import StructureUpdatePage from './structure-update.page-object';
import StructureDetailsPage from './structure-details.page-object';

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

describe('Structure e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: StructureUpdatePage;
  let detailsPage: StructureDetailsPage;
  let listPage: StructureComponentsPage;
  let deleteDialog: StructureDeleteDialog;
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

  it('should load Structures', async () => {
    await navBarPage.getEntityPage('structure');
    listPage = new StructureComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Structure page', async () => {
      await listPage.createButton.click();
      updatePage = new StructureUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/clarisseApp.structure.home.createOrEditLabel/);
    });

    it('should create and save Structures', async () => {
      await updatePage.codeStructureInput.sendKeys('codeStructure');

      await updatePage.dateDeCreationInput.sendKeys('2001-01-01');

      await updatePage.dateDeClotureInput.sendKeys('2001-01-01');

      await updatePage.codeAmeliInput.sendKeys('codeAmeli');

      await updatePage.codeSirpasInput.sendKeys('codeSirpas');

      await updatePage.codeReprographieInput.sendKeys('codeReprographie');

      await updatePage.articleInput.sendKeys('article');

      await updatePage.libelleInput.sendKeys('libelle');

      await updatePage.libelleCourtInput.sendKeys('libelleCourt');

      await updatePage.libelleLongInput.sendKeys('libelleLong');

      await updatePage.ordreInput.sendKeys('5');

      await updatePage.publicationSurInternetInput.sendKeys('publicationSurInternet');

      // await selectLastOption(updatePage.typeStructureSelect);

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

        deleteDialog = new StructureDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/clarisseApp.structure.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Structure page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.last());
        await click(detailsButton);

        detailsPage = new StructureDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Structure page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.last());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await updatePage.codeStructureInput.clear();
        await updatePage.codeStructureInput.sendKeys('modified');

        await updatePage.dateDeCreationInput.clear();
        await updatePage.dateDeCreationInput.sendKeys('2019-01-01');

        await updatePage.dateDeClotureInput.clear();
        await updatePage.dateDeClotureInput.sendKeys('2019-01-01');

        await updatePage.codeAmeliInput.clear();
        await updatePage.codeAmeliInput.sendKeys('modified');

        await updatePage.codeSirpasInput.clear();
        await updatePage.codeSirpasInput.sendKeys('modified');

        await updatePage.codeReprographieInput.clear();
        await updatePage.codeReprographieInput.sendKeys('modified');

        await updatePage.articleInput.clear();
        await updatePage.articleInput.sendKeys('modified');

        await updatePage.libelleInput.clear();
        await updatePage.libelleInput.sendKeys('modified');

        await updatePage.libelleCourtInput.clear();
        await updatePage.libelleCourtInput.sendKeys('modified');

        await updatePage.libelleLongInput.clear();
        await updatePage.libelleLongInput.sendKeys('modified');

        await clear(updatePage.ordreInput);
        await updatePage.ordreInput.sendKeys('6');

        await updatePage.publicationSurInternetInput.clear();
        await updatePage.publicationSurInternetInput.sendKeys('modified');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
