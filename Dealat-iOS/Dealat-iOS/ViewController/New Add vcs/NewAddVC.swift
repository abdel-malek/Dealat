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

class NewAddVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout, UITextViewDelegate,UITextFieldDelegate,YMSPhotoPickerViewControllerDelegate {
    
    @IBOutlet weak var collectionView : UICollectionView!
    var homeVC : HomeVC?
    var ad : AD!
    
    let cellIdentifier = "imageCellIdentifier"
    
    //    var images: NSArray! = []
    var imagesPaths: [String] = []
    var imagesAssets: [UIImage]! = [UIImage]()
    var imagesPathsDeleted: [String] = []
    
    var videoUrl : URL!
    var videoPath : String!
    var videoPathDeleted : String!

    
    @IBOutlet var tfields: [SkyFloatingLabelTextField]!
    
    @IBOutlet weak var tfTitle : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCity : SkyFloatingLabelTextField!
    @IBOutlet weak var tfLocation : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCategory : SkyFloatingLabelTextField!
    @IBOutlet weak var tfPeriod : SkyFloatingLabelTextField!
    @IBOutlet weak var tfPrice : SkyFloatingLabelTextField!
    @IBOutlet weak var negotiableSwitch : UISwitch!
    @IBOutlet weak var featuredSwitch : UISwitch!
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
    @IBOutlet weak var tfSize : SkyFloatingLabelTextField!
    
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
    
    
    var cities = [City]()
    var locations = [Location]()
    var typesBase = [Type]()
    var types = [Type]()
    var schedules = [Schedule]()
    var educations = [Education]()
    var periods = [Period]()
    
    
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
                self.resetFields()
                
                let nn = Cat.getName(selectedCategory.category_id.intValue)
                
                self.tfCategory.text = nn//selectedCategory.category_name
                self.tfCategory.adjustsFontSizeToFitWidth = true
                self.tfCategory.minimumFontSize = 8
                
                self.setupTypes()
                self.refreshData()
            }
        }
    }
    
    
    var selectedCity : City!{
        didSet{
            self.selectedLocation = nil
            
            if let city = selectedCity{
                self.tfCity.text = city.city_name
            }else{
                self.tfCity.text = nil
            }
        }
    }
    
    var selectedLocation : Location!{
        didSet{
            if let loc = selectedLocation{
                self.tfLocation.text = loc.location_name
            }else{
                self.tfLocation.text = nil
            }
        }
    }
    
    var selectedPeriod : Period!{
        didSet{
            if let period = selectedPeriod{
                self.tfPeriod.text = period.name
            }else{
                self.tfPeriod.text = nil
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
    
    
    
    func ifHidden(index : IndexPath) -> Bool{
        
        if let cat = self.selectedCategory,cat.hidden_fields != nil {
            
            switch (index.section,index.row){
                
            case (1,0): return cat.hidden_fields.contains("type_name")
            case (1,1): return cat.hidden_fields.contains("type_model_name")
            case (1,2): return cat.hidden_fields.contains("manufacture_date")
            case (1,3): return cat.hidden_fields.contains("is_automatic")
            case (1,4): return cat.hidden_fields.contains("is_new")
            case (1,5): return cat.hidden_fields.contains("kilometer")
                
            case (2,0): return cat.hidden_fields.contains("space")
            case (2,1): return cat.hidden_fields.contains("rooms_num")
            case (2,2): return cat.hidden_fields.contains("floor")
            case (2,3): return cat.hidden_fields.contains("state")
            case (2,4): return cat.hidden_fields.contains("furniture")
                
            case (3,0): return cat.hidden_fields.contains("type_name")
            case (3,1): return cat.hidden_fields.contains("state")
                
            case (4,0): return cat.hidden_fields.contains("type_name")
            case (4,1): return cat.hidden_fields.contains("state")
            case (4,2): return cat.hidden_fields.contains("size")
                
            case (5,0): return cat.hidden_fields.contains("state")
            case (6,0): return cat.hidden_fields.contains("state")
            case (7,0): return cat.hidden_fields.contains("state")
                
            case (8,0): return cat.hidden_fields.contains("education")
            case (8,1): return cat.hidden_fields.contains("schedule")
            case (8,2): return cat.hidden_fields.contains("experience")
            case (8,3): return cat.hidden_fields.contains("salary")
                
            case (9,0): return cat.hidden_fields.contains("state")
                
            default:
                break
            }
        }
        
        return false
    }
    
    var editMode : Bool{
        return self.ad != nil
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        setupViews()
        
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (cities ,locations, types, educations, schedules, periods) in
            
            self.cities = cities
            self.typesBase = types
            self.locations = locations
            self.educations = educations
            self.schedules = schedules
            self.periods = periods
            
            if self.editMode{
                //                Communication.shared.get_ad_details(ad_id: self.ad.ad_id.intValue, template_id: self.ad.tamplate_id.intValue, callback: { (res) in
                //
                //                    self.hideLoading()
                //                    self.ad = res
                //                    self.refreshData()
                //                    self.setDataForEdit()
                //
                //                })
                
                self.hideLoading()
                self.refreshData()
                self.setDataForEdit()
                
                
            }else{
                self.hideLoading()
            }
            
            
        }
    }
    
    func setupTypes(){
        types = typesBase.filter({$0.tamplate_id.intValue == self.selectedCategory.tamplate_id.intValue})
    }
    
    func setupViews(){
        
        if self.editMode{
            self.title = "SellEdit".localized
        }else{
            self.title = "Sell".localized
        }
        
        
        tfDescription.text = "Description".localized
        tfDescription.delegate = self
        tfDescription.textColor = UIColor.white
        
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
        
        
        
        self.setPickerViewOn(self.tfCity)
        self.setPickerViewOn(self.tfLocation)
        self.setPickerViewOn(self.tfPeriod)
        
        
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
            i.delegate = self
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
        tfSize.text = nil
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
        tfSize.isEnabled = false
        tfType4.isEnabled = false
        tfStatus5.isEnabled = false
        tfStatus6.isEnabled = false
        tfStatus7.isEnabled = false
        tfSchedule.isEnabled = false
        tfEducation.isEnabled = false
        tfExperince.isEnabled = false
        tfSalary.isEnabled = false
        tfStatus9.isEnabled = false
        
        let tamplate_id = (self.editMode) ? self.ad.tamplate_id.intValue : self.selectedCategory.tamplate_id.intValue
        
        switch tamplate_id {
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
            tfSize.isEnabled = true
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
    
    func getDataFromUrl(url: URL, completion: @escaping (Data?, URLResponse?, Error?) -> ()) {
        URLSession.shared.dataTask(with: url) { data, response, error in
            completion(data, response, error)
            }.resume()
    }
    
    
    func setDataForEdit(){
        if self.editMode{
            
            self.tfTitle.text = ad.title
            if let categoty_id = self.ad.category_id{
                if let cat = Provider.shared.catsFull.first(where: {$0.category_id.intValue == categoty_id.intValue}){
                    self.selectedCategory = cat
                }
            }
            
            if let city_id = self.ad.city_id{
                if let city = self.cities.first(where: {$0.city_id.intValue == city_id.intValue}){
                    self.selectedCity = city
                }
            }
            
            print("LOCCCC : \(self.ad.location_id)")
            
            if let location_id = self.ad.location_id{
                print(self.locations.count)
                if let loc = self.locations.first(where: {$0.location_id.intValue == location_id.intValue}){
                    print("sssss : \(loc.location_name)")
                    
                    self.selectedLocation = loc
                }
            }
            
            if let show_period_id = self.ad.show_period{
                if let period = self.periods.first(where: {$0.show_period_id.intValue == show_period_id.intValue}){
                    self.selectedPeriod = period
                }
            }
            
            self.tfPrice.text = ad.price.stringValue
            self.negotiableSwitch.setOn(ad.is_negotiable.Boolean, animated: false)
            self.featuredSwitch.setOn(ad.is_featured.Boolean, animated: false)
            self.tfDescription.text = ad.description
            
            
            print("COUNT IMAGES : \(self.ad.images.count)")
            
            self.imagesPaths.append(self.ad.main_image)
            self.imagesAssets.append(UIImage.init())


            for i in self.ad.images{
                self.imagesPaths.append(i.image)
                self.imagesAssets.append(UIImage.init())
            }
            
            if let vid = self.ad.main_video, !vid.isEmpty{
                self.videoPath = vid
                self.videoUrl = URL.init(string: Communication.shared.baseImgsURL + vid)
            }
            
            
            print("COUNT imagesPaths : \(imagesPaths.count)")
            self.collectionView.reloadData()
            
            switch self.ad.tamplate_id.intValue{
            case 1:
                self.selectedYear =  self.years.index(where: {$0 == self.ad.vehicle.manufacture_date})
                
                self.selectedTransmission = self.ad.vehicle.is_automatic.intValue
                
                self.selectedStatus = self.ad.vehicle.is_new.intValue
                
                self.tfKilometers.text = self.ad.vehicle.kilometer.stringValue
                
                if let type_id = self.ad.vehicle.type_id{
                    if let type = self.typesBase.first(where: {$0.type_id.intValue == type_id.intValue}){
                        self.selectedType = type
                    }
                }
                
                if let model_id = self.ad.vehicle.type_model_id{
                    if let model = self.self.selectedType.models.first(where: {$0.type_model_id.intValue == model_id.intValue}){
                        self.selectedModel = model
                    }
                }
                
                
                
                //                if let type = self.ad.vehicle.
                //
                //                self.type_nameLbl.text = ad.vehicle.type_name
                //                self.type_model_nameLbl.text = ad.vehicle.type_model_name
                
                
                break
                
            default:
                break
            }
        }
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if self.ifHidden(index: indexPath){
            return 0
        }
        
        if self.selectedCategory != nil{
            if self.selectedCategory.tamplate_id.intValue == 8{
                if indexPath == IndexPath(row: 6, section: 0){
                    return 0
                }
            }
        }
        
        
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
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        self.resetFields()
        
        return true
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
            
            guard !self.editMode else{
                return
            }
            
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
    
    
    func resetFields(){
        for i in self.tfields{
            i.errorMessage = nil
        }
    }
    
    func validMessage(tf : SkyFloatingLabelTextField! = nil, message : String){
        
        if tf != nil{
            tf.errorMessage = "required".localized
        }
        
        self.showErrorMessage(text: message)
    }
    
    
    @IBAction func submitAction(){
        var params : [String : Any] = [:]
        
        resetFields()
        
        guard let titleAd = self.tfTitle.text, !titleAd.isEmpty else {
            self.validMessage(tf: tfTitle ,message : "Please enter".localized +  "Title".localized)
            return
        }
        guard let cat = self.selectedCategory, let category_id = cat.category_id else {
            self.validMessage(tf: tfCategory ,message : "Please enter".localized +  "Category".localized)
            return
        }
        guard let city = self.selectedCity, let city_id = city.city_id else {
            self.validMessage(tf: tfCity ,message : "Please enter".localized +  "City".localized)
            return
        }
        
        guard let loc = self.selectedLocation, let location_id = loc.location_id else {
            self.validMessage(tf: tfLocation ,message : "Please enter".localized +  "Area".localized)
            return
        }
        
        guard let period = self.selectedPeriod, let show_period_id = period.show_period_id else {
            self.validMessage(tf: tfPeriod ,message : "Please enter".localized +  "ShowPeriod".localized)
            return
        }
        
        guard let desAd = self.tfDescription.text, !desAd.isEmpty else {
            self.validMessage(message : "Please enter".localized +  "Description".localized)
            return
        }
        
        
        if self.selectedCategory.tamplate_id.intValue != 8{
            guard let price = self.tfPrice.text, !price.isEmpty, (Int(price) != nil) else {
                self.validMessage(tf : tfPrice, message : "Please enter".localized +  "Price".localized)
                return
            }
            params["price"] = price
        }
        
        guard  !self.imagesPaths.isEmpty else {
            self.validMessage( message : "Please add images".localized)
            return
        }
        
        params["city_id"] = city_id.intValue
        params["location_id"] = location_id.intValue
        params["show_period"] = show_period_id.intValue
        
        params["is_negotiable"] = self.negotiableSwitch.isOn ? 1 : 0
        params["is_featured"] = self.featuredSwitch.isOn ? 1 : 0
        
        
        switch self.selectedCategory.tamplate_id.intValue {
        case 1:
            guard let type = self.selectedType, let type_id = type.type_id else {
                self.validMessage(tf : self.tfType, message : "Please enter".localized + "TypeName".localized)
                return
            }
            guard let model = self.selectedModel, let type_model_id = model.type_model_id else {
                self.validMessage(tf : self.tfModel, message : "Please enter".localized + "TypeModelName".localized)
                return
            }
            guard let manufacture_date = self.selectedYear else {
                self.validMessage(tf : self.tfYear, message : "Please enter".localized + "Year".localized)
                return
            }
            guard let is_automatic = self.selectedTransmission else {
                self.validMessage(tf : self.tfTrans, message : "Please enter".localized + "IsAutomatic".localized)
                return
            }
            guard let is_new = self.selectedStatus else {
                self.validMessage(tf : self.tfStatus, message : "Please enter".localized + "State".localized)
                return
            }
            
            guard let kilometer = self.tfKilometers.text, !kilometer.isEmpty else {
                self.validMessage(tf : self.tfKilometers, message : "Please enter".localized + "Kilometer".localized)
                return
            }
            
            
            params["type_id"] = type_id.intValue
            params["type_model_id"] = type_model_id.intValue
            params["manufacture_date"] = self.years[manufacture_date]
            params["is_automatic"] = is_automatic
            params["is_new"] = is_new
            params["kilometer"] = kilometer
            
            
        case 2:
            guard let status = self.tfStatus2.text, !status.isEmpty else {
                self.validMessage(tf : self.tfStatus2, message : "Please enter".localized + "State".localized)
                return
            }
            
            guard let rooms = self.tfRooms_num.text, !rooms.isEmpty else {
                self.validMessage(tf : self.tfRooms_num, message : "Please enter".localized + "Rooms".localized)
                return
            }
            guard let floor = self.tfFloor.text, !floor.isEmpty else {
                self.validMessage(tf : self.tfFloor, message : "Please enter".localized + "Floor".localized)
                return
            }
            guard let space = self.tfSpace.text, !space.isEmpty else {
                self.validMessage(tf : self.tfSpace, message : "Please enter".localized + "Space".localized)
                return
            }
            
            params["state"] = status
            params["rooms_num"] = rooms
            params["floor"] = floor
            params["space"] = space
            //            params["is_new"] = is_new
            params["with_furniture"] = tfWith_furniture.isOn ? 1 : 0
            
        case 3:
            guard let is_new = self.selectedStatus else {
                self.validMessage(tf : self.tfStatus3, message : "Please enter".localized + "State".localized)
                return
            }
            guard let type = self.selectedType, let type_id = type.type_id else {
                self.validMessage(tf : self.tfType3, message : "Please enter".localized + "TypeName".localized)
                return
            }
            
            params["type_id"] = type_id.intValue
            params["is_new"] = is_new
            
        case 4:
            
            params["is_new"] = self.tfSize.text!
        case 5,6,7,9:
            guard let is_new = self.selectedStatus else {
                self.validMessage(message : "Please enter".localized + "State".localized)
                return
            }
            
            params["is_new"] = is_new
            
            
        case 8:
            guard let schedule = self.selectedSchedule, let schedule_id = schedule.schedual_id else {
                self.validMessage(tf : self.tfSchedule, message : "Please enter".localized + "Schedule".localized)
                return
            }
            
            guard let education = self.selectedEducation, let education_id = education.education_id else {
                self.validMessage(tf : self.tfEducation, message : "Please enter".localized + "Education".localized)
                return
            }
            
            guard let experience = self.tfExperince.text, !experience.isEmpty else {
                self.validMessage(tf : self.tfExperince, message : "Please enter".localized + "Experience".localized)
                return
            }
            
            guard let salary = self.tfSalary.text, !salary.isEmpty else {
                self.validMessage(tf : self.tfSalary, message : "Please enter".localized + "Salary".localized)
                return
            }
            
            params["schedule_id"] = schedule_id.intValue
            params["education_id"] = education_id.intValue
            params["experience"] = experience
            params["salary"] = salary
            
        default:
            break
        }
        
        
        for i in self.imagesPathsDeleted{
            var imagesArray = [String]()
            imagesArray.append(i)
            
            if let yy = JSON(imagesArray).rawString(){
                params["deleted_images"] = yy
            }
        }
        
        if let i = self.videoPathDeleted{
            params["deleted_videos"] = i
        }
        
        if let i = self.videoPath{
            params["main_video"] = i
        }
        
        
        if self.editMode{
            
            self.showLoading()
            Communication.shared.edit_item(ad_id: self.ad.ad_id.intValue, category_id: category_id.intValue, title: titleAd, description: desAd, images: self.imagesPaths,paramsAdditional: params) { (res) in
                self.hideLoading()
                
                self.performSegue(withIdentifier: "unwindSegueToVC1", sender: res)
                
                
                /*self.navigationController?.popViewController(animated: true)
                if let vcs = self.navigationController?.viewControllers{
                    for i in vcs{
                        if i.isKind(of: AdDetailsBaseVC.self){
                            if let vc = i as? AdDetailsBaseVC{
                                //                                vc.adDetailsVC.getRefreshing()
                                
                                Communication.shared.get_ad_details(ad_id: self.ad.ad_id.intValue, template_id: self.ad.tamplate_id.intValue) { (res) in
                                    vc.adDetailsVC.hideLoading()
                                    vc.adDetailsVC.ad = res
                                    vc.ad = res
                                    
                                    let im = IMG()
                                    im.image = self.ad.main_image
                                    self.ad.images.insert(im, at: 0)
                                    
                                    vc.refreshBar()
                                    vc.adDetailsVC.refreshData()
                                }
                                
                                vc.showErrorMessage(text: res.message)
                            }
                        }
                    }
                }*/
                
            }
            
            
        }else{
            self.showLoading()
            Communication.shared.post_new_ad(category_id: category_id.intValue, title: titleAd, description: desAd, images: self.imagesPaths,paramsAdditional : params) { (res) in
                self.hideLoading()
                
                self.navigationController?.popViewController(animated: true)
                self.homeVC?.showErrorMessage(text: res.message)
            }
        }
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "unwindSegueToVC1"{
            if let vc = segue.destination as? AdDetailsBaseVC{
                if let value = sender as? CustomResponse{
                    vc.msg = value.message
                }
            }
        }
    }
    
    
}

//MARK: CollectionView
extension NewAddVC{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 8
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if indexPath.row == 0{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
            
            cell.newAddVC = self
            cell.videoPath = self.videoPath
            
            return cell
        }else{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
            let img : UIImage! = (imagesAssets  != nil && indexPath.row - 1 < imagesAssets.count) ?  self.imagesAssets[indexPath.row - 1] : nil
            cell.newAddVC = self
            cell.imageNew = (indexPath.row - 1,img)
            
            return cell
        }
        
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let w = collectionView.frame.width - 12
        return CGSize(width: w / 4, height: w / 4)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row + 1)
        
        
        if indexPath.row == 0{
            
            self.selectVideoAction()
            
        }else{
            if indexPath.row - 1 >= self.imagesPaths.count{
                let pickerViewController = YMSPhotoPickerViewController.init()
                
                var cnt : Int = 7
                cnt -= (self.imagesAssets != nil) ? self.imagesAssets.count  : 0
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
                let alert = UIAlertController.init(title: nil, message: nil, preferredStyle: .actionSheet)
                
                
                alert.addAction(UIAlertAction.init(title: "Make as Main".localized, style: .default, handler: { (ac) in
                    
                    //                let mutableImages: NSMutableArray! = NSMutableArray.init(array: self.imagesAssets)
                    var mutableImages : [UIImage]! = self.imagesAssets
                    let temp = mutableImages[indexPath.row - 1]
                    mutableImages.remove(at: indexPath.row - 1)
                    mutableImages.insert(temp, at: 0)
                    self.imagesAssets = mutableImages
                    
                    if indexPath.row - 1 < self.imagesPaths.count{
                        let temp2 = self.imagesPaths[indexPath.row - 1]
                        self.imagesPaths.remove(at: indexPath.row - 1)
                        self.imagesPaths.insert(temp2, at: 0)
                    }
                    
                    self.collectionView.reloadData()
                }))
                
                
                alert.addAction(UIAlertAction.init(title: "Delete Photo".localized, style: .destructive, handler: { (ac) in
                    
                    self.deletePhotoImage(indexPath.row - 1)
                    self.collectionView.reloadData()
                }))
                
                alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
                
                self.present(alert, animated: true, completion: nil)
            }
        }
    }
}


//MARK: Photos YangMingShan
extension NewAddVC{
    
    @objc func deletePhotoImage(_ index: Int) {
        var mutableImages: [UIImage]! = self.imagesAssets
        mutableImages.remove(at: index)
        self.imagesAssets =  mutableImages
        
        if index < self.imagesPaths.count{
            self.imagesPathsDeleted.append(self.imagesPaths[index])
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
            self.imagesAssets = [image]
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
            
            var mutableImages: [UIImage]! = [UIImage]()
            
            //            let w = self.collectionView.frame.width - 12
            //            let customSize = CGSize(width: w / 4, height: w / 4)
            
            for i in self.imagesAssets{
                mutableImages.append(i)
            }
            
            for asset: PHAsset in photoAssets
            {
                imageManager.requestImageData(for: asset, options: options, resultHandler: { (dat, ss, oo, rr) in
                    self.imagesPaths.append("")
                    mutableImages.append(UIImage.init(data: dat!)!)
                })
            }
            
            
            self.imagesAssets = mutableImages
            self.collectionView.reloadData()
        }
    }
}


extension NewAddVC : UIPickerViewDelegate, UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        var cnt = 0
        
        switch pickerView.tag {
        case -1: // Cites
            cnt = self.cities.count
        case 1: // Locations
            if let c = self.selectedCity{
                cnt = c.locations.count
            }
        case -2:
            cnt = self.periods.count
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
        
        
        return (cnt == 0) ? 1 : cnt + 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if row == 0{
            return "select one"
        }
        
        switch pickerView.tag {
        case -1: // Cities
            return self.cities[row - 1].city_name
        case 1: // Locations
            if let c = self.selectedCity{
                return c.locations[row - 1].location_name
            }
            return nil
        case -2:
            return self.periods[row - 1].name
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
        
        
        if row != 0 {
            self.resetFields()
        }
        
        switch pickerView.tag {
        case -1: // Cities
            if row == 0{
                self.selectedCity = nil
                return
            }
            self.selectedCity = self.cities[row - 1]
        case 1: // Locations
            if row == 0{
                self.selectedLocation = nil
                return
            }
            
            if let c = self.selectedCity{
                self.selectedLocation =  c.locations[row - 1]
            }
        case -2:
            if row == 0{
                self.selectedPeriod = nil
                return
            }
            
            self.selectedPeriod =  self.periods[row - 1]
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


extension NewAddVC : UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func SelectVideo(){
        // UIImagePickerController is a view controller that lets a user pick media from their photo library.
        let imagePickerController = UIImagePickerController()
        
        // Only allow photos to be picked, not taken.
        imagePickerController.sourceType = .photoLibrary
        imagePickerController.mediaTypes = ["public.movie"]
        
        // Make sure ViewController is notified when the user picks an image.
        imagePickerController.delegate = self
        
        imagePickerController.title = "Videos"
        present(imagePickerController, animated: true, completion: nil)
    }
    
    func captureVideo(){
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera){
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerControllerSourceType.camera;
            imagePicker.mediaTypes = ["public.movie"]
            imagePicker.cameraCaptureMode = .video
            imagePicker.allowsEditing = false
            self.present(imagePicker, animated: true, completion: nil)
        }
    }

    
    
    @objc func selectVideoAction() {
        let alert = UIAlertController.init(title: nil, message: nil, preferredStyle: .actionSheet)
        
        alert.addAction(UIAlertAction.init(title: "Capture video".localized, style: .default, handler: { (ac) in
            self.captureVideo()
        }))
        
        alert.addAction(UIAlertAction.init(title: "Choose video".localized, style: .default, handler: { (ac) in
            self.SelectVideo()
        }))
        
        if self.videoUrl != nil{
            alert.addAction(UIAlertAction.init(title: "Delete".localized, style: .destructive, handler: { (ac) in
                
                self.videoPathDeleted = self.videoPath
                self.videoUrl = nil
                self.videoPath = nil
                self.collectionView.reloadData()
                
            }))

        }
        
        alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }
    
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Dismiss the picker if the user canceled.
        
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        
        if let url = info[UIImagePickerControllerMediaURL] as? URL{
            print("UIImagePickerControllerMediaURL :\(url.absoluteString)" )
            
            self.videoUrl = url
            
            Alamofire.upload(
                multipartFormData: { multipartFormData in
                    
                        multipartFormData.append(url, withName: "video")
                    
            },
                to: "\(Communication.shared.baseURL + Communication.shared.item_video_uploadURL)" ,headers : Communication.shared.getHearders(),
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
                                    
                                    self.videoPath = value.data.stringValue
                                    self.collectionView.reloadData()

                                    
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
                        //                    self.hideLoading()
                        print(encodingError)
                    }
            })

        }

        // Dismiss the picker.
        dismiss(animated: true, completion: nil)
    }
    
}


