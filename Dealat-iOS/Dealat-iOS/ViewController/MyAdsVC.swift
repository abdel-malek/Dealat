//
//  MyAdsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/5/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import XLPagerTabStrip
import DropDown

class MyAdsVC: BaseVC,UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout,IndicatorInfoProvider {
    
    @IBOutlet weak var collectionView : UICollectionView!
    
//    var adsFull = [AD]()
    var ads = [AD]()

    var itemInfo = IndicatorInfo(title: "My Ads".localized)
    
    var statusDropDown = DropDown()
    @IBOutlet weak var tfStatus : UITextField!
    var selectedStatus = 0
    
    var pageNumber = 1
    var isDataLoading : Bool = false
    var isAllDataFetched : Bool = false
    let indicater : UIActivityIndicatorView = UIActivityIndicatorView(style: .gray)

    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        
        Provider.setScreenName("My Ads")

        setupStatus()
    }
    
    override func setupViews() {
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        self.collectionView.addSubview(ref)
    }
    
    override func getRefreshing() {
        self.isDataLoading = false
        self.isAllDataFetched = false
        self.pageNumber = 1
        
        getRefreshing2()
    }
    
    func getRefreshing2(){
        Communication.shared.get_my_items(page_num: self.pageNumber,status : self.selectedStatus) { (res) in
            self.hideLoading()
            
            self.onDataRecived(res)
        }
    }
    
    func onDataRecived(_ res : [AD]){
        self.hideLoading()
        self.collectionView.isHidden = false
        isDataLoading = false
        
        if self.pageNumber == 1{
            self.ads = res
        }else{
            self.ads = self.ads + res
        }
        
        if (res.count < Provider.PAGE_SIZE)
        {
            isAllDataFetched = true
        }

        self.collectionView.reloadData()
    }
    
    func loadItems()
    {
        
        if(isDataLoading || isAllDataFetched)
        {
            return
        }
        
        isDataLoading = true
        pageNumber = pageNumber + 1
        
        indicater.startAnimating()
        indicater.hidesWhenStopped = true
        indicater.frame.size.height = 40

        getRefreshing2()
    }
    
    func setupStatus(){
        
        var array = [String]()
        
        array.append("All".localized)
        array.append("Pending".localized)
        array.append("Accepted".localized)
        array.append("Expired".localized)
        array.append("Hidden".localized)
        array.append("Rejected".localized)
        
        self.selectedStatus = 0
        self.tfStatus.text = "All".localized
        
        statusDropDown.textFont = Theme.Font.Calibri.withSize(17)
        statusDropDown.dataSource = array
        statusDropDown.anchorView = self.tfStatus
        statusDropDown.direction = .bottom
        statusDropDown.bottomOffset = CGPoint(x: 0, y: tfStatus.bounds.height)
        
        statusDropDown.selectionAction = { [unowned self] (index, item) in
            self.tfStatus.text = item
            self.selectedStatus = index
            
            self.getRefreshing()
            
//            self.refreshData()
        }
    }
    
    /*
    func refreshData(){
        
        switch self.selectedStatus {
        case 0:
            self.ads = self.adsFull
        case 1:
            self.ads = self.adsFull.filter({ (a) -> Bool in
                return a.status.intValue == 1
            })
        case 2:
            self.ads = self.adsFull.filter({ (a) -> Bool in
                if let ea = a.expired_after{
                    return a.status.intValue == 2 && (ea.intValue >= 0)
                }
                return a.status.intValue == 2
            })
        case 3:
            self.ads = self.adsFull.filter({ (a) -> Bool in
                if let ea = a.expired_after{
                    return a.status.intValue == 2 && (ea.intValue < 0)
                }
                return false
            })
        case 4:
            self.ads = self.adsFull.filter({ (a) -> Bool in
                return a.status.intValue == 4
            })
        case 5:
            self.ads = self.adsFull.filter({ (a) -> Bool in
                return a.status.intValue == 5
            })
        default:
            break
        }
        self.collectionView.reloadData()
    }
 */
 
    @IBAction func openStatusDropDown(){
        self.statusDropDown.show()
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return ads.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! AdCell
        cell.ad = self.ads[indexPath.row]
        cell.fromMyAds = true
        
        //check if it is the last item at the list
        if(indexPath.row == self.ads.count - 1)
        {
            loadItems()
        }
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
        vc.tamplateId = self.ads[indexPath.row].tamplate_id.intValue
        vc.ad = self.ads[indexPath.row]
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width / 2)
    }
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return self.itemInfo
    }
    
}

