//
//  NewAddVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import Photos
import YangMingShan
import DropDown
import Alamofire
import KMPlaceholderTextView
import SkyFloatingLabelTextField
import SwiftyJSON
//import IQDropDownTextField

class NewAddVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout, UITextViewDelegate,YMSPhotoPickerViewControllerDelegate {
    
    @IBOutlet weak var collectionView : UICollectionView!
    var homeVC : HomeVC!
    
    
    let cellIdentifier = "imageCellIdentifier"
    var images: NSArray! = []
    
    var imagesPaths: [String] = []
    
    @IBOutlet var tfields: [SkyFloatingLabelTextField]!
    
    @IBOutlet weak var tfTitle : SkyFloatingLabelTextField!
    @IBOutlet weak var tfLocation : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCategory : SkyFloatingLabelTextField!
    @IBOutlet weak var tfPrice : SkyFloatingLabelTextField!
    @IBOutlet weak var negotiableSwitch : UISwitch!
    @IBOutlet weak var tfDescription : UITextView!
    
    // 1
    @IBOutlet weak var tfType : SkyFloatingLabelTextField!
    @IBOutlet weak var tfModel : SkyFloatingLabelTextField!
    @IBOutlet weak var tfYear : SkyFloatingLabelTextField!
    @IBOutlet weak var tfTrans : SkyFloatingLabelTextField!
    @IBOutlet weak var tfStatus : SkyFloatingLabelTextField!
    @IBOutlet weak var tfKilometers : SkyFloatingLabelTextField!
    
    // 2
    @IBOutlet weak var tfStatus2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfRooms_num : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloor : SkyFloatingLabelTextField!
    @IBOutlet weak var tfWith_furniture : UISwitch!
    @IBOutlet weak var tfSpace : SkyFloatingLabelTextField!
    
    //3
    @IBOutlet weak var tfStatus3 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfType3 : SkyFloatingLabelTextField!
    
    //4
    @IBOutlet weak var tfStatus4 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfType4 : SkyFloatingLabelTextField!
    
    //5
    @IBOutlet weak var tfStatus5 : SkyFloatingLabelTextField!
    
    //6
    @IBOutlet weak var tfStatus6 : SkyFloatingLabelTextField!
    
    //7
    @IBOutlet weak var tfStatus7 : SkyFloatingLabelTextField!
    
    //8
    @IBOutlet weak var tfSchedule : SkyFloatingLabelTextField!
    @IBOutlet weak var tfEducation : SkyFloatingLabelTextField!
    @IBOutlet weak var tfExperince : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSalary : SkyFloatingLabelTextField!
    
    //9
    @IBOutlet weak var tfStatus9 : SkyFloatingLabelTextField!
    
    
    
    var locations = [Location]()
    var typesBase = [Type]()
    var types = [Type]()
    var schedules = [Schedule]()
    var educations = [Education]()
    
    
    var years : [String] {
        var arr = [String]()
        for i in 1970...2018{
            arr.append("\(i)")
        }
        return arr
    }
    
    var transmission : [String] {
        return ["Manual".localized,"Automatic".localized]
    }
    
    var status : [String] {
        return ["old".localized,"new".localized]
    }
    
    
    var selectedCategory : Cat!{
        didSet{
            if self.tfCategory != nil{
                let nn = Cat.getName(selectedCategory.category_id.intValue)
                
                //                var arr = nn.components(separatedBy: "-")
                //                arr = arr.reversed()
                //                print(arr)
                //                let t = arr.joined(separator: "-")
                
                self.tfCategory.text = nn//selectedCategory.category_name
                self.tfCategory.adjustsFontSizeToFitWidth = true
                self.tfCategory.minimumFontSize = 8
                
                self.setupTypes()
                self.refreshData()
            }
        }
    }
    
    var selectedLocation : Location!{
        didSet{
            if let loc = selectedLocation{
                var name = ""
                name += loc.city_name != nil ? loc.city_name! + " - " : ""
                name += loc.location_name != nil ? loc.location_name! : ""
                self.tfLocation.text = name
            }else{
                self.tfLocation.text = nil
            }
        }
    }
    
    var selectedType : Type!{
        didSet{
            self.tfType.text = (selectedType != nil) ? selectedType.name : nil
            self.tfType3.text = (selectedType != nil) ? selectedType.name : nil
        }
    }
    var selectedModel : Model!{
        didSet{
            self.tfModel.text = (selectedModel != nil) ? selectedModel.name : nil
        }
    }
    
    var selectedYear : Int!{
        didSet{
            self.tfYear.text = (selectedYear != nil) ? self.years[selectedYear] : nil
        }
    }
    var selectedTransmission : Int!{
        didSet{
            self.tfTrans.text = (selectedTransmission != nil) ? self.transmission[selectedTransmission] : nil
        }
    }
    var selectedStatus : Int!{
        didSet{
            let status = (selectedStatus != nil) ? self.status[selectedStatus] : nil
            self.tfStatus.text = status
            self.tfStatus3.text = status
            self.tfStatus4.text = status
            self.tfStatus5.text = status
            self.tfStatus6.text = status
            self.tfStatus7.text = status
            self.tfStatus9.text = status
        }
    }
    
    var selectedSchedule : Schedule!{
        didSet{
            self.tfSchedule.text = (selectedSchedule != nil) ? selectedSchedule.name : nil
        }
    }
    
    var selectedEducation : Education!{
        didSet{
            self.tfEducation.text = (selectedEducation != nil) ? selectedEducation.name : nil
        }
    }
    
    
    //    var selectedKilometers : String!{
    //        didSet{
    //            self.tfKilometers.text = selectedKilometers
    //        }
    //    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        setupViews()
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, types, educations, schedules) in
            self.hideLoading()
            
            self.typesBase = types
            self.locations = locations
            self.educations = educations
            self.schedules = schedules
            //            self.setupLocations()
        }
    }
    
    func setupViews(){
        
        //        tfDescription.placeholder = "Description".localized
        //        tfDescription.placeholderColor = .white
        
        
        tfDescription.text = "Description".localized
        tfDescription.delegate = self
        tfDescription.textColor = UIColor.white
        
        //        tfDescription.becomeFirstResponder()
        tfDescription.selectedTextRange = tfDescription.textRange(from: tfDescription.beginningOfDocument, to: tfDescription.beginningOfDocument)
        
        
        // CollectionView
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        
        // Background
        let img = UIImageView.init(image: #imageLiteral(resourceName: "Background-blur"))
        img.contentMode = .scaleAspectFill
        img.clipsToBounds = true
        self.tableView.backgroundView = img
        
        // Navigation Item
        //        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(title: "Reset", style: .plain, target: self, action: #selector(self.reset))
        self.title = "Sell".localized
        
        
        self.setPickerViewOn(self.tfLocation)
        //1
        self.setPickerViewOn(self.tfType)
        self.setPickerViewOn(self.tfModel)
        self.setPickerViewOn(self.tfYear)
        self.setPickerViewOn(self.tfTrans)
        self.setPickerViewOn(self.tfStatus)
        //3
        self.setPickerViewOn(self.tfType3)
        self.setPickerViewOn(self.tfStatus3)
        //4
        self.setPickerViewOn(self.tfType4)
        self.setPickerViewOn(self.tfStatus4)
        //5
        self.setPickerViewOn(self.tfStatus5)
        //6
        self.setPickerViewOn(self.tfStatus6)
        //7
        self.setPickerViewOn(self.tfStatus7)
        //8
        self.setPickerViewOn(self.tfSchedule)
        self.setPickerViewOn(self.tfEducation)
        
        //9
        self.setPickerViewOn(self.tfStatus9)
        
        
        // IQDropDownTextField
        for i in tfields{
            i.lineColor = .clear
            i.lineHeight = 0
            i.selectedLineColor = .clear
            i.selectedLineHeight = 0
            
            i.selectedTitleColor = UIColor.lightGray
            i.placeholderColor = Theme.Color.White
            i.titleColor = UIColor.lightGray
            i.textColor = .white
            
            if let place = i.placeholder{
                let arr = place.components(separatedBy: "*")
                if let temp = arr.first{
                    i.placeholder = temp.localized + "*"
                }
            }
        }
        
        /*for i in tfields2{
         if let place = i.placeholder{
         let arr = place.components(separatedBy: "*")
         if let temp = arr.first{
         i.placeholder = temp.localized + "*"
         i.placeHolderColor = Theme.Color.White
         
         }
         }
         }*/
        
        
    }
    
    
    /*func textField(_ textField: IQDropDownTextField, didSelectItem item: String?) {
     if textField == tfType{
     self.setupModels()
     }
     }*/
    
    
    func setPickerViewOn(_ textField : UITextField){
        
        // UIPickerView
        let pickerView = UIPickerView(frame:CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: 216))
        pickerView.delegate = self
        pickerView.dataSource = self
        
        //        let c = UIColor.groupTableViewBackground
        let c = UIColor.init(red: 215/255, green: 217/255, blue: 223/255, alpha: 1)
        pickerView.setValue(c, forKey: "backgroundColor")
        
        textField.inputView = pickerView
        pickerView.tag = textField.tag
        
        // ToolBar
        //        let toolBar = UIToolbar()
        //        toolBar.barStyle = .default
        //        toolBar.isTranslucent = true
        //        toolBar.tintColor = UIColor(red: 92/255, green: 216/255, blue: 255/255, alpha: 1)
        //        toolBar.sizeToFit()
        
        // Adding Button ToolBar
        //        let doneButton = UIBarButtonItem(title: "Done", style: .plain, target: self, action: nil)
        //        let spaceButton = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        //        let cancelButton = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: nil)
        //        toolBar.setItems([cancelButton, spaceButton, doneButton], animated: false)
        //        toolBar.isUserInteractionEnabled = true
        //        textField.inputAccessoryView = toolBar
    }
    
    func refreshData(){
        
        tfType.text = nil
        tfModel.text = nil
        tfYear.text = nil
        tfTrans.text = nil
        tfStatus.text = nil
        tfKilometers.text = nil
        tfStatus2.text = nil
        tfRooms_num.text = nil
        tfFloor.text = nil
        tfSpace.text = nil
        tfStatus3.text = nil
        tfType3.text = nil
        tfStatus4.text = nil
        tfType4.text = nil
        tfStatus5.text = nil
        tfStatus6.text = nil
        tfStatus7.text = nil
        tfSchedule.text = nil
        tfEducation.text = nil
        tfExperince.text = nil
        tfSalary.text = nil
        tfStatus9.text = nil
        
        tfType.isEnabled = false
        tfModel.isEnabled = false
        tfYear.isEnabled = false
        tfTrans.isEnabled = false
        tfStatus.isEnabled = false
        tfKilometers.isEnabled = false
        tfStatus2.isEnabled = false
        tfRooms_num.isEnabled = false
        tfFloor.isEnabled = false
        tfSpace.isEnabled = false
        tfStatus3.isEnabled = false
        tfType3.isEnabled = false
        tfStatus4.isEnabled = false
        tfType4.isEnabled = false
        tfStatus5.isEnabled = false
        tfStatus6.isEnabled = false
        tfStatus7.isEnabled = false
        tfSchedule.isEnabled = false
        tfEducation.isEnabled = false
        tfExperince.isEnabled = false
        tfSalary.isEnabled = false
        tfStatus9.isEnabled = false
        
        
        
        switch self.selectedCategory.tamplate_id.intValue {
        case 1:
            tfType.isEnabled = true
            tfModel.isEnabled = true
            tfYear.isEnabled = true
            tfTrans.isEnabled = true
            tfStatus.isEnabled = true
            tfKilometers.isEnabled = true
        case 2:
            tfStatus2.isEnabled = true
            tfRooms_num.isEnabled = true
            tfFloor.isEnabled = true
            tfSpace.isEnabled = true
        case 3:
            tfStatus3.isEnabled = true
            tfType3.isEnabled = true
        case 4:
            tfStatus4.isEnabled = true
            tfType4.isEnabled = true
        case 5:
            tfStatus5.isEnabled = true
        case 6:
            tfStatus6.isEnabled = true
        case 7:
            tfStatus7.isEnabled = true
        case 8:
            tfSchedule.isEnabled = true
            tfEducation.isEnabled = true
            tfExperince.isEnabled = true
            tfSalary.isEnabled = true
        case 9:
            tfStatus9.isEnabled = true
        default:
            break
        }
        
        selectedType = nil
        selectedModel = nil
        selectedYear = nil
        selectedTransmission = nil
        selectedStatus = nil
        selectedSchedule = nil
        selectedEducation = nil
        
        self.tableView.reloadData()
    }
    
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            return (indexPath.row == 0) ? UITableViewAutomaticDimension : 54
        case 11:
            return UITableViewAutomaticDimension
        default:
            if self.selectedCategory != nil{
                return (self.selectedCategory.tamplate_id.intValue == indexPath.section) ? 54 : 0
            }else{
                return 0
            }
        }
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        
        // Combine the textView text and the replacement text to
        // create the updated text string
        let currentText:String = textView.text
        let updatedText = (currentText as NSString).replacingCharacters(in: range, with: text)
        
        // If updated text view will be empty, add the placeholder
        // and set the cursor to the beginning of the text view
        if updatedText.isEmpty {
            
            textView.text = "Description".localized
            textView.textColor = UIColor.white
            
            textView.selectedTextRange = textView.textRange(from: textView.beginningOfDocument, to: textView.beginningOfDocument)
            
            return false
        }
            
            // Else if the text view's placeholder is showing and the
            // length of the replacement string is greater than 0, clear
            // the text view and set its color to black to prepare for
            // the user's entry
        else if textView.text == "Description".localized && !text.isEmpty {
            textView.text = nil
            textView.textColor = UIColor.white
        }
        
        
        
        let size = textView.bounds.size
        let newSize = textView.sizeThatFits(CGSize(width: size.width,
                                                   height: CGFloat.maximum(size.width, size.height)))
        
        // Resize the cell only when cell's size is changed
        if size.height != newSize.height {
            UIView.setAnimationsEnabled(false)
            tableView?.beginUpdates()
            tableView?.endUpdates()
            UIView.setAnimationsEnabled(true)
            
            let thisIndexPath = IndexPath.init(row: 0, section: 11)
            
            tableView.scrollToRow(at: thisIndexPath, at: .bottom, animated: false)
        }
        
        return true
    }
    
    
    func textViewDidChangeSelection(_ textView: UITextView) {
        if self.view.window != nil {
            if textView.text == "Description".localized {
                textView.selectedTextRange = textView.textRange(from: textView.beginningOfDocument, to: textView.beginningOfDocument)
            }
        }
    }
    
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.section == 0,indexPath.row == 2{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC2") as! ChooseCatVC
            
            let c = Cat()
            c.category_name = "ChooseCategory".localized
            c.children = Provider.shared.cats
            
            vc.cat = c
            vc.newAdd = self
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            self.present(vc, animated: true, completion: nil)
        }
        if indexPath.section == 0,indexPath.row == 3{
            //            self.locationDropDown.show()
        }
    }
    
    override func tableView(_ tableView: UITableView, willDisplayHeaderView view: UIView, forSection section: Int) {
        (view as! UITableViewHeaderFooterView).backgroundView?.backgroundColor = UIColor.clear
    }
    
    override func tableView(_ tableView: UITableView, willDisplayFooterView view: UIView, forSection section: Int) {
        (view as! UITableViewHeaderFooterView).backgroundView?.backgroundColor = UIColor.clear
    }
    
    
    @objc func reset(){
        print(self.imagesPaths)
    }
    
    func isValidForm() -> Bool{
        return true
    }
    
    func validMessage(_ message : String){
        self.showErrorMessage(text: message)
    }
    
    @IBAction func submitAction(){
        var params : [String : Any] = [:]
        
        guard let titleAd = self.tfTitle.text, !titleAd.isEmpty else {
            self.validMessage("Please enter title")
            return
        }
        guard let cat = self.selectedCategory, let category_id = cat.category_id else {
            self.validMessage("Please select category")
            return
        }
        guard let loc = self.selectedLocation, let location_id = loc.location_id else {
            self.validMessage("Please select location")
            return
        }
        guard let desAd = self.tfDescription.text, !desAd.isEmpty else {
            self.validMessage("Please enter description")
            return
        }
        guard let price = self.tfPrice.text, !price.isEmpty, (Int(price) != nil) else {
            self.validMessage("Please enter price")
            return
        }
        guard  !self.imagesPaths.isEmpty else {
            self.validMessage("Please add images")
            return
        }
        
        params["is_negotiable"] = self.negotiableSwitch.isOn ? 1 : 0
        
        
        switch self.selectedCategory.tamplate_id.intValue {
        case 1:
            guard let type = self.selectedType, let type_id = type.type_id else {
                self.validMessage("Please select Type")
                return
            }
            guard let model = self.selectedModel, let type_model_id = model.type_model_id else {
                self.validMessage("Please select model")
                return
            }
            guard let manufacture_date = self.selectedYear else {
                self.validMessage("Please select year")
                return
            }
            guard let is_automatic = self.selectedTransmission else {
                self.validMessage("Please select Transmission")
                return
            }
            guard let is_new = self.selectedStatus else {
                self.validMessage("Please select state")
                return
            }
            
            guard let kilometer = self.tfKilometers.text, !kilometer.isEmpty else {
                self.validMessage("Please select Kilometers")
                return
            }
            
            params["type_id"] = type_id.intValue
            params["type_model_id"] = type_model_id.intValue
            params["manufacture_date"] = self.years[manufacture_date]
            params["is_automatic"] = is_automatic
            params["is_new"] = is_new
            params["kilometer"] = kilometer
            
            
        case 2:
            guard let is_new = self.selectedStatus else {
                self.validMessage("Please select state")
                return
            }
            
            guard let rooms = self.tfRooms_num.text, !rooms.isEmpty else {
                self.validMessage("Please select rooms")
                return
            }
            guard let floor = self.tfFloor.text, !floor.isEmpty else {
                self.validMessage("Please select floor")
                return
            }
            guard let space = self.tfSpace.text, !space.isEmpty else {
                self.validMessage("Please select space")
                return
            }
            
            params["state"] = is_new
            params["rooms_num"] = rooms
            params["floor"] = floor
            params["space"] = space
            params["is_new"] = is_new
            params["with_furniture"] = tfWith_furniture.isOn ? 1 : 0
            
        case 3:
            guard let is_new = self.selectedStatus else {
                self.validMessage("Please select state")
                return
            }
            guard let type = self.selectedType, let type_id = type.type_id else {
                self.validMessage("Please select Type")
                return
            }
            
            params["type_id"] = type_id.intValue
            params["is_new"] = is_new
            
        case 5,6,7,9:
            guard let is_new = self.selectedStatus else {
                self.validMessage("Please select state")
                return
            }
            
            params["is_new"] = is_new
            
            
        case 8:
            guard let schedule = self.selectedSchedule, let schedule_id = schedule.schedual_id else {
                self.validMessage("Please select schedule")
                return
            }
            
            guard let education = self.selectedEducation, let education_id = education.education_id else {
                self.validMessage("Please select education")
                return
            }
            
            guard let experience = self.tfExperince.text, !experience.isEmpty else {
                self.validMessage("Please enter experince")
                return
            }
            
            guard let salary = self.tfSalary.text, !salary.isEmpty else {
                self.validMessage("Please enter salary")
                return
            }
            
            
            params["schedule_id"] = schedule_id.intValue
            params["education_id"] = education_id.intValue
            params["experience"] = experience
            params["salary"] = salary
            
        default:
            break
        }
        
        
        self.showLoading()
        Communication.shared.post_new_ad(category_id: category_id.intValue, location_id: location_id.intValue, show_period: 1, title: titleAd, description: desAd, price: price, images: self.imagesPaths,paramsAdditional : params) { (res) in
            self.hideLoading()
            
            self.navigationController?.popViewController(animated: true)
            self.homeVC.showErrorMessage(text: res.message)
            
        }
        
    }
    
}

//MARK: CollectionView
extension NewAddVC{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 8
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
        let img : UIImage! = (images != nil && indexPath.row < images.count) ?  self.images.object(at: indexPath.row) as? UIImage : nil
        cell.newAddVC = self
        cell.imageNew = (indexPath.row,img)
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let w = collectionView.frame.width - 12
        return CGSize(width: w / 4, height: w / 4)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row + 1)
        
        if indexPath.row >= self.images.count{
            let pickerViewController = YMSPhotoPickerViewController.init()
            
            var cnt : Int = 8
            cnt -= (self.images != nil) ? self.images.count  : 0
            pickerViewController.numberOfPhotoToSelect = UInt(cnt)
            
            pickerViewController.theme.titleLabelTextColor = Theme.Color.White
            pickerViewController.theme.navigationBarBackgroundColor = Theme.Color.red
            pickerViewController.theme.tintColor = Theme.Color.White
            pickerViewController.theme.orderTintColor = Theme.Color.red
            pickerViewController.theme.orderLabelTextColor = Theme.Color.White
            pickerViewController.theme.cameraVeilColor = Theme.Color.red
            pickerViewController.theme.cameraIconColor = UIColor.white
            pickerViewController.theme.statusBarStyle = .lightContent
            
            self.yms_presentCustomAlbumPhotoView(pickerViewController, delegate: self)
        }else{
            let alert = UIAlertController.init(title: "Would you like to:", message: "", preferredStyle: .actionSheet)
            
            
            alert.addAction(UIAlertAction.init(title: "Make as Main", style: .default, handler: { (ac) in
                
                let mutableImages: NSMutableArray! = NSMutableArray.init(array: self.images)
                let temp = mutableImages.object(at: indexPath.row)
                mutableImages.removeObject(at: indexPath.row)
                mutableImages.insert(temp, at: 0)
                self.images = NSArray.init(array: mutableImages)
                
                if indexPath.row < self.imagesPaths.count{
                    let temp2 = self.imagesPaths[indexPath.row]
                    self.imagesPaths.remove(at: indexPath.row)
                    self.imagesPaths.insert(temp2, at: indexPath.row)
                }
                
                self.collectionView.reloadData()
            }))
            
            
            alert.addAction(UIAlertAction.init(title: "Delete Photo", style: .destructive, handler: { (ac) in
                
                self.deletePhotoImage(indexPath.row)
                
                
                self.collectionView.reloadData()
            }))
            
            
            
            alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
            
            self.present(alert, animated: true, completion: nil)
            
        }
    }
}


//MARK: Photos YangMingShan
extension NewAddVC{
    
    @objc func deletePhotoImage(_ index: Int) {
        let mutableImages: NSMutableArray! = NSMutableArray.init(array: images)
        mutableImages.removeObject(at: index)
        self.images = NSArray.init(array: mutableImages)
        
        if index < self.imagesPaths.count{
            self.imagesPaths.remove(at: index)
        }
        
        self.collectionView.reloadData()
    }
    
    // MARK: - YMSPhotoPickerViewControllerDelegate
    
    func photoPickerViewControllerDidReceivePhotoAlbumAccessDenied(_ picker: YMSPhotoPickerViewController!) {
        let alertController = UIAlertController.init(title: "Allow photo album access?", message: "Need your permission to access photo albumbs", preferredStyle: .alert)
        let dismissAction = UIAlertAction.init(title: "Cancel", style: .cancel, handler: nil)
        let settingsAction = UIAlertAction.init(title: "Settings", style: .default) { (action) in
            UIApplication.shared.openURL(URL.init(string: UIApplicationOpenSettingsURLString)!)
        }
        alertController.addAction(dismissAction)
        alertController.addAction(settingsAction)
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func photoPickerViewControllerDidReceiveCameraAccessDenied(_ picker: YMSPhotoPickerViewController!) {
        let alertController = UIAlertController.init(title: "Allow camera album access?", message: "Need your permission to take a photo", preferredStyle: .alert)
        let dismissAction = UIAlertAction.init(title: "Cancel", style: .cancel, handler: nil)
        let settingsAction = UIAlertAction.init(title: "Settings", style: .default) { (action) in
            UIApplication.shared.openURL(URL.init(string: UIApplicationOpenSettingsURLString)!)
        }
        alertController.addAction(dismissAction)
        alertController.addAction(settingsAction)
        
        // The access denied of camera is always happened on picker, present alert on it to follow the view hierarchy
        picker.present(alertController, animated: true, completion: nil)
    }
    
    func photoPickerViewController(_ picker: YMSPhotoPickerViewController!, didFinishPicking image: UIImage!) {
        picker.dismiss(animated: true) {
            self.images = [image]
            self.collectionView.reloadData()
        }
    }
    
    func photoPickerViewController(_ picker: YMSPhotoPickerViewController!, didFinishPickingImages photoAssets: [PHAsset]!) {
        
        picker.dismiss(animated: true) {
            let imageManager = PHImageManager.init()
            let options = PHImageRequestOptions.init()
            options.deliveryMode = .highQualityFormat
            options.resizeMode = .exact
            options.isSynchronous = true
            
            let mutableImages: NSMutableArray! = []
            
            //            let w = self.collectionView.frame.width - 12
            //            let customSize = CGSize(width: w / 4, height: w / 4)
            
            for i in self.images{
                mutableImages.add(i)
            }
            
            for asset: PHAsset in photoAssets
            {
                
                imageManager.requestImageData(for: asset, options: options, resultHandler: { (dat, ss, oo, rr) in
                    self.imagesPaths.append("")
                    mutableImages.add(UIImage.init(data: dat!)!)
                })
                
            }
            
            
            self.images = mutableImages.copy() as? NSArray
            self.collectionView.reloadData()
        }
    }
    
    
    /*func uploadImage(_ img : UIImage){
     
     let path = savePhotoLocal(img)
     
     Alamofire.upload(
     multipartFormData: { multipartFormData in
     
     if let p = path{
     multipartFormData.append(p, withName: "image")
     }else{
     multipartFormData.append("".getData, withName: "image")
     }
     
     },
     to: "http://192.168.9.53/Dealat/index.php/api/ads_control/ad_images_upload/format/json" ,headers : Communication.shared.getHearders(),
     encodingCompletion: { encodingResult in
     
     switch encodingResult {
     case .success(let upload, _, _):
     
     upload.uploadProgress(closure: { (Progress) in
     print("Upload Progress: \(Progress.fractionCompleted)")
     if Progress.fractionCompleted == 1{
     }
     })
     
     upload.responseObject { (response : DataResponse<CustomResponse>) in
     debugPrint(response)
     
     Communication.shared.output(response)
     self.hideLoading()
     
     switch response.result{
     case .success(let value):
     
     if value.status{
     
     
     }else{
     notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
     }
     break
     case .failure(let error):
     print(error.localizedDescription)
     notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
     break
     }
     
     }
     case .failure(let encodingError):
     self.hideLoading()
     print(encodingError)
     }
     })
     
     
     }
     
     func savePhotoLocal(_ img : UIImage) -> URL?{
     let tt = img.resized(toWidth: 512)
     
     var imageData = UIImagePNGRepresentation(tt!)
     
     let imageSize: Int = imageData!.count
     print("size of image in KB: %f ", imageSize / 1024)
     
     if (imageSize / 1024) > 2000 {
     
     return nil
     }
     
     if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
     
     let path = dir.appendingPathComponent("img.png")
     
     //writing
     do {
     try imageData!.write(to: path, options: Data.WritingOptions.atomic)
     }
     catch {}
     }
     
     let rrr = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
     
     return  rrr.appendingPathComponent("img.png")
     }*/
    
    
}

// IQTEXTFIELD
extension NewAddVC{
    
    func setupTypes(){
        types = typesBase.filter({$0.tamplate_id.intValue == self.selectedCategory.tamplate_id.intValue})
    }
    
    
    /*var selectedLocation : Location!{
     let row = self.tfLocation.selectedRow
     if row != -1, self.locations.count > row{
     return self.locations[row]
     }
     return nil
     }
     
     var selectedType : Type!{
     let row = self.tfType.selectedRow
     if row != -1, self.types.count > row{
     return self.types[row]
     }
     return nil
     }
     
     
     var selectedModel : Model!{
     let row = self.tfModel.selectedRow
     if row != -1, self.selectedType.models.count > row{
     return self.selectedType.models[row]
     }
     return nil
     }
     
     
     func setupLocations(){
     let list = self.locations.map({"\($0.city_name!) - \($0.location_name!)"})
     tfLocation.itemList = list
     tfLocation.itemListUI = list
     tfLocation.dropDownMode = .textPicker
     tfLocation.isOptionalDropDown = true
     tfLocation.delegate = self
     }
     
     
     func setupModels(){
     let list = self.selectedType.models.map({"\($0.name!)"})
     tfModel.itemList = list
     tfModel.itemListUI = list
     tfModel.dropDownMode = .textPicker
     tfModel.isOptionalDropDown = true
     tfModel.delegate = self
     }
     
     func setupTypes(){
     types = typesBase.filter({$0.tamplate_id.intValue == self.selectedCategory.tamplate_id.intValue})
     
     let list = self.types.map({"\($0.name!)"})
     tfType.itemList = list
     tfType.itemListUI = list
     tfType.dropDownMode = .textPicker
     tfType.isOptionalDropDown = true
     tfType.delegate = self
     }
     */
    
}

extension NewAddVC : UIPickerViewDelegate, UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        var cnt = 0
        
        switch pickerView.tag {
        case 1: // Locations
            cnt = self.locations.count
        case 2,13,15: // Types
            cnt =  self.types.count
        case 3: // Models
            if self.selectedType != nil{
                cnt =  self.selectedType.models.count
            }else{
                cnt =  0
            }
        case 4: // Years
            cnt =  self.self.years.count
        case 5: // Transmission
            cnt =  self.transmission.count
        case 6,14,16,17,18,19,20: // Status
            cnt =  self.status.count
        case 21:
            cnt =  self.schedules.count
        case 22:
            cnt = self.educations.count
        default:
            cnt =  0
        }
        
        
        return (cnt == 0) ? 0 : cnt + 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if row == 0{
            return "select one"
        }
        
        switch pickerView.tag {
        case 1: // Locations
            let loc = self.locations[row - 1]
            var name = ""
            name += loc.city_name != nil ? loc.city_name! + " - " : ""
            name += loc.location_name != nil ? loc.location_name! : ""
            return name
        case 2,13,15: // Types
            return self.types[row - 1].name
        case 3: // Models
            return self.self.selectedType.models[row - 1].name
        case 4: // Years
            return self.self.years[row - 1]
        case 5: // Transmission
            return self.transmission[row - 1]
        case 6,14,16,17,18,19,20: // Status
            return self.status[row - 1]
        case 21:
            return self.schedules[row - 1].name
        case 22:
            return self.educations[row - 1].name
            
        default:
            return ""
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        switch pickerView.tag {
        case 1: // Locations
            if row == 0{
                self.selectedLocation = nil
                return
            }
            self.selectedLocation = self.locations[row - 1]
        case 2,13,15: // Types
            if row == 0{
                self.selectedType = nil
                return
            }
            self.selectedType = self.types[row - 1]
        case 3: // Models
            if row == 0{
                self.selectedModel = nil
                return
            }
            if let type = self.selectedType, selectedType.models.count > row - 1{
                self.selectedModel = type.models[row - 1]
            }
        case 4: // Years
            if row == 0{
                self.selectedYear = nil
                return
            }
            self.selectedYear = row - 1
        case 5: // Transmission
            if row == 0{
                self.selectedTransmission = nil
                return
            }
            self.selectedTransmission = row - 1
        case 6,14,16,17,18,19,20: // Status
            if row == 0{
                self.selectedStatus = nil
                return
            }
            self.selectedStatus = row - 1
        case 21:
            if row == 0{
                self.selectedSchedule = nil
                return
            }
            self.selectedSchedule = self.schedules[row - 1]
        case 22:
            if row == 0{
                self.selectedEducation = nil
                return
            }
            self.selectedEducation = self.educations[row - 1]
        default:
            break
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        if row == 0{
            return NSAttributedString.init(string: "select one", attributes: [NSAttributedStringKey.foregroundColor : UIColor.lightGray])
        }
        return nil
    }
    
    
}
