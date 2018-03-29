//
//  EditProfileVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import SkyFloatingLabelTextField
import SwiftyJSON
import Alamofire
import DatePickerDialog
import AFDateHelper

class EditProfileVC: BaseVC {
    
    @IBOutlet var tfields: [SkyFloatingLabelTextField]!
    
    @IBOutlet weak var imgProfile: UIImageView!
    
    @IBOutlet weak var phoneLbl: UILabel!
    @IBOutlet weak var tfName: SkyFloatingLabelTextField!
    @IBOutlet weak var tfCity: SkyFloatingLabelTextField!
    @IBOutlet weak var tfEmail: SkyFloatingLabelTextField!
    @IBOutlet weak var tfWhatsapp: SkyFloatingLabelTextField!
    @IBOutlet weak var tfBirthday: SkyFloatingLabelTextField!
    @IBOutlet weak var tfGender: SkyFloatingLabelTextField!

    var genders = [("Male".localized, 1),("Famale".localized,2)]
    
    var image_updated : Bool = false
    var fromRegister : Bool = false
    
    var cities = [City]()
    var selectedCity : City!{
        didSet{
            self.tfCity.text = (selectedCity != nil) ? selectedCity.name : nil
        }
    }
    var selectedGenderIndex : Int! {
        didSet{
            self.tfGender.text = (selectedGenderIndex != nil) ? genders[selectedGenderIndex].0 : nil
        }
    }
    
    var pickerView = UIPickerView()
    var homeVC : HomeVC!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        getData()
    }
    
    override func getRefreshing() {
        
        Communication.shared.get_countries { (res) in
            self.cities = res
            
            Communication.shared.get_my_info({ (res2) in
                self.hideLoading()
                self.refreshData()
                
                print("CITY ID : \(Provider.getCity())")
                if let f = res.filter({$0.city_id.intValue == Provider.getCity()}).first{
                    self.selectedCity = f
                }
                if let f = self.genders.index(where: {res2.gender == $0.1}){
                    self.selectedGenderIndex = f
                }

                
            })
        }
    }
    
    override func setupViews(){
        
        
        self.title = "Edit Profile".localized
        
        let tap = UITapGestureRecognizer.init(target: self, action: #selector(self.selectPhotoAction))
        tap.numberOfTapsRequired = 1
        
        self.imgProfile.isUserInteractionEnabled = true
        self.imgProfile.addGestureRecognizer(tap)
        
        
        setPickerViewOn(self.tfCity)
        setPickerViewOn(self.tfGender)

        
        
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
        
        self.refreshData()
    }
    
    
   @IBAction func openDate(){
        DatePickerDialog().show("Select date".localized, doneButtonTitle: "OK".localized, cancelButtonTitle: "Cancel".localized, defaultDate: Date(timeIntervalSince1970: 0), minimumDate: nil, maximumDate: Date(), datePickerMode: UIDatePickerMode.date) { (res) in
            
            if let d = res {
                self.tfBirthday.text = d.toString(format: DateFormatType.isoDate)
            }
        }
    }
    
    func refreshData(){
        let me = User.getCurrentUser()
        
        self.tfName.text = me.name
        self.tfEmail.text = me.email
        self.phoneLbl.text = me.phone
        self.tfWhatsapp.text = me.whatsup_number
        self.tfBirthday.text = me.birthday

        
        if me.personal_image != nil && !me.personal_image.isEmpty{
            Provider.sd_setImage(self.imgProfile, urlString: me.personal_image)
        }
        
        self.pickerView.reloadAllComponents()
    }
    
    func setPickerViewOn(_ textField : UITextField){
        // UIPickerView
        pickerView = UIPickerView(frame:CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: 216))
        pickerView.delegate = self
        pickerView.dataSource = self
        
        //        let c = UIColor.groupTableViewBackground
        let c = UIColor.init(red: 215/255, green: 217/255, blue: 223/255, alpha: 1)
        pickerView.setValue(c, forKey: "backgroundColor")
        
        textField.inputView = pickerView
        pickerView.tag = textField.tag
    }
    
    
    @IBAction func saveAction(){
//        let me = User.getCurrentUser()
        let name = tfName.text!
        let email = tfEmail.text!
        let whatsapp = tfWhatsapp.text!
        let birthday = self.tfBirthday.text!

        
        guard !name.isEmpty else {
            self.showErrorMessage(text: "Please enter name")
            return
        }
        
        guard (!email.isEmpty && Provider.isValidEmail(email)) || email.isEmpty else {
            self.showErrorMessage(text: "Please enter a valid email")
            return
        }
        
        guard !whatsapp.isEmpty else {
            self.showErrorMessage(text: "Please enter a valid whatsapp number")
            return
        }
        
        guard self.selectedCity != nil else {
            self.showErrorMessage(text: "Please select city")
            return
        }
        
        
        self.showLoading()
        var path : URL!
        
        if self.image_updated{
            path = savePhotoLocal(self.imgProfile.image!)
        }
        
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                
                if let p = path{
                    multipartFormData.append(p, withName: "personal_image")
                }else{
                    multipartFormData.append("".getData, withName: "personal_image")
                }
                
                multipartFormData.append(name.getData, withName: "name")
                multipartFormData.append(email.getData, withName: "email")
                multipartFormData.append(self.selectedCity.city_id.stringValue.getData, withName: "city_id")
                multipartFormData.append(whatsapp.getData, withName: "whatsup_number")

                multipartFormData.append(birthday.getData, withName: "birthday")
                
                if let g = self.selectedGenderIndex{
                    multipartFormData.append("\(self.genders[g].1)".getData, withName: "gender")
                }
                
        },
            to: "\(Communication.shared.baseURL + Communication.shared.edit_user_infoURL)" ,headers : Communication.shared.getHearders(),
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
                                
                                if !self.fromRegister{
                                if let i = value.data, let obj = i.dictionaryObject{
                                    let newUser = User.getObject(obj)
                                    User.saveMe(me: newUser)
                                }
                                
                                self.navigationController?.popViewController(animated: true)
                                    self.homeVC.showErrorMessage(text: value.message)
                                }else{
                                    let me = User.getCurrentUser()
                                    me.statues_key = User.USER_STATUES.USER_REGISTERED.rawValue
                                    User.saveMe(me: me)
                                    AppDelegate.setupViews()
                                }
                                    
                                    
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
    
    
}

extension EditProfileVC : UIPickerViewDelegate, UIPickerViewDataSource{
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        return (pickerView.tag == 1)  ? self.cities.count + 1 : genders.count + 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if row == 0{
            return "select one"
        }
        
        if pickerView.tag == 1{
        let c = self.cities[row - 1]
        return c.name
        }else{
            return self.genders[row - 1].0
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        
        if row == 0{
            if pickerView.tag == 1{
                self.selectedCity = nil
            }else{
                self.selectedGenderIndex = nil
            }
            
            
            return
        }
        
        if pickerView.tag == 1{
            self.selectedCity = self.cities[row - 1]
        }else{
            self.selectedGenderIndex = row - 1
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        if row == 0{
            return NSAttributedString.init(string: "select one", attributes: [NSAttributedStringKey.foregroundColor : UIColor.lightGray])
        }
        return nil
    }
    
    func SelectPhoto(){
        // UIImagePickerController is a view controller that lets a user pick media from their photo library.
        let imagePickerController = UIImagePickerController()
        
        // Only allow photos to be picked, not taken.
        imagePickerController.sourceType = .photoLibrary
        
        // Make sure ViewController is notified when the user picks an image.
        imagePickerController.delegate = self
        
        present(imagePickerController, animated: true, completion: nil)
        
        
    }
    
    func SelectCamera(){
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera){
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerControllerSourceType.camera;
            imagePicker.allowsEditing = false
            self.present(imagePicker, animated: true, completion: nil)
        }
    }
    
    
   @objc func selectPhotoAction() {
        let alert = UIAlertController.init(title: nil, message: nil, preferredStyle: .actionSheet)
        
        alert.addAction(UIAlertAction.init(title: "Take a picture".localized, style: .default, handler: { (ac) in
            self.SelectCamera()
        }))
        
        alert.addAction(UIAlertAction.init(title: "Choose an Image".localized, style: .default, handler: { (ac) in
            self.SelectPhoto()
        }))
        
        alert.addAction(UIAlertAction.init(title: "Delete Photo".localized, style: .destructive, handler: { (ac) in
            self.imgProfile.image = #imageLiteral(resourceName: "pic.png")
        }))
        
        alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }
    
}


extension EditProfileVC : UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Dismiss the picker if the user canceled.
        
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        let selectedImage = info[UIImagePickerControllerOriginalImage] as! UIImage
        
        // Set photoImageView to display the selected image.
        imgProfile.image = selectedImage
        
        self.image_updated = true
        
        // Dismiss the picker.
        dismiss(animated: true, completion: nil)
    }
    
    
    func savePhotoLocal(_ img : UIImage) -> URL?{
        let tt = img.resized(toWidth: 512)
        
        var imageData = UIImagePNGRepresentation(tt!)
        
        let imageSize: Int = imageData!.count
        print("size of image in KB: %f ", imageSize / 1024)
        
        if (imageSize / 1024) > 2000 {
            
            return nil
        }
        let random = CGFloat.random()
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
            
            let path = dir.appendingPathComponent("imgProfile\(random).png")
            
            //writing
            do {
                try imageData!.write(to: path, options: Data.WritingOptions.atomic)
            }
            catch {}
        }
        
        let rrr = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        
        return  rrr.appendingPathComponent("imgProfile\(random).png")
    }

    
}

